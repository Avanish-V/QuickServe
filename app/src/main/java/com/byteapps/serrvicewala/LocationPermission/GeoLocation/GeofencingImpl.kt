package com.byteapps.wiseschool.GeoFencing.GeoLocation

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.byteapps.serrvicewala.LocationPermission.CheckAvailabilityDataModel
import com.byteapps.serrvicewala.LocationPermission.UserLocationAddress
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.wiseschool.GeoFencing.LatLngDataModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GeofencingImpl @Inject constructor(private val db: FirebaseFirestore) : GeofencingInterface {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    @SuppressLint("MissingPermission")
    override suspend fun startLocationUpdate(context: Context): Flow<ResultState<UserLocationAddress>> =
        callbackFlow {

            trySend(ResultState.Loading)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


            val locationRequest = LocationRequest().apply {
                // Sets the desired interval for
                // active location updates.
                // This interval is inexact.
                interval = TimeUnit.SECONDS.toMillis(60)

                // Sets the fastest rate for active location updates.
                // This interval is exact, and your application will never
                // receive updates more frequently than this value
                fastestInterval = TimeUnit.SECONDS.toMillis(30)

                // Sets the maximum time when batched location
                // updates are delivered. Updates may be
                // delivered sooner than this interval
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)

                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }


            try {

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        locationResult.lastLocation?.let { location ->


                            val geocoder = Geocoder(context)
                            val addresses =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            val address = addresses?.get(0)!!.getAddressLine(0)
                            val state = addresses?.get(0)!!.adminArea
                            val zip = addresses?.get(0)!!.postalCode
                            trySend(
                                ResultState.Success(
                                    UserLocationAddress(

                                        address = address,
                                        state = state,
                                        pinCode = zip

                                    )
                                )
                            )
                        }
                    }
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

//                val removeTask = fusedLocationClient.removeLocationUpdates(locationCallback)
//
//                removeTask.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(TAG, "Location Callback removed.")
//                    } else {
//                        Log.d(TAG, "Failed to remove Location Callback.")
//                    }
//                }

            } catch (e: Exception) {

                trySend(ResultState.Error(e.message.toString()))
            }

            awaitClose {
                close()
            }

        }


    @SuppressLint("MissingPermission")
    override suspend fun checkAvailability(context: Context): Flow<ResultState<CheckAvailabilityDataModel>> = callbackFlow {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        geocoder = Geocoder(context, Locale.getDefault())


        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {

                val latitude = location.latitude
                val longitude = location.longitude

                val districtName = getDistrictName(latitude, longitude, geocoder)

                val currentAddress = getCurrentLocation(latitude, longitude, geocoder)

                trySend(ResultState.Success(

                    CheckAvailabilityDataModel(
                        currentLocation = currentAddress,
                        isAvailable = districtName == "Agra Division"
                    )

                ))



            } else {


            }
        }.addOnFailureListener { e ->
                trySend(ResultState.Error(e.message.toString()))
        }

        awaitClose {
            close()
        }


    }


}

private fun getDistrictName(latitude: Double, longitude: Double, geocoder: Geocoder): String {

    var districtName = ""

    try {

        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (!addresses.isNullOrEmpty()) {

            val address: Address = addresses[0]

            districtName = address.subAdminArea

        }

    } catch (e: IOException) {

        e.printStackTrace()

    }

    return districtName
}

private fun getCurrentLocation(latitude: Double, longitude: Double, geocoder: Geocoder): String {

    var currentAddress = ""

    try {

        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (!addresses.isNullOrEmpty()) {


             currentAddress = addresses.get(0).getAddressLine(0)



        }

    } catch (e: IOException) {

        e.printStackTrace()

    }

    return currentAddress
}