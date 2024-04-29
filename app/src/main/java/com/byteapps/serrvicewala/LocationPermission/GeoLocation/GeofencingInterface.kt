package com.byteapps.wiseschool.GeoFencing.GeoLocation

import android.content.Context
import com.byteapps.serrvicewala.LocationPermission.CheckAvailabilityDataModel
import com.byteapps.serrvicewala.LocationPermission.UserLocationAddress
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.wiseschool.GeoFencing.LatLngDataModel
import kotlinx.coroutines.flow.Flow

interface GeofencingInterface {

   suspend fun startLocationUpdate(context: Context):Flow<ResultState<UserLocationAddress>>

   suspend fun checkAvailability(context: Context):Flow<ResultState<CheckAvailabilityDataModel>>

}