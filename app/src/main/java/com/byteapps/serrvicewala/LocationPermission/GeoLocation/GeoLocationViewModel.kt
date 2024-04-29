package com.byteapps.wiseschool.GeoFencing.GeoLocation

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.LocationPermission.CheckAvailabilityDataModel
import com.byteapps.serrvicewala.LocationPermission.UserLocationAddress
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.wiseschool.GeoFencing.LatLngDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeoLocationViewModel @Inject constructor(private val geofencingInterface: GeofencingInterface): ViewModel() {


    private val _locationAddress: MutableState<LatLngResultState> = mutableStateOf(LatLngResultState())
    val locationAddress: State<LatLngResultState> = _locationAddress

    fun startLocationUpdate(context: Context){

        viewModelScope.launch {
            geofencingInterface.startLocationUpdate(context).collect{
                when(it){
                    is ResultState.Loading->{
                        _locationAddress.value = LatLngResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _locationAddress.value = LatLngResultState(locationAddress = it.data)
                    }
                    is ResultState.Error->{
                        _locationAddress.value = LatLngResultState(error= it.message.toString())
                    }
                }
            }
        }
    }

    private val _isAvailable: MutableState<AvailabilityResultState> = mutableStateOf(AvailabilityResultState())
    val isAvailable: State<AvailabilityResultState> = _isAvailable


    fun checkAvailability(context: Context){

        viewModelScope.launch {
            geofencingInterface.checkAvailability(context).collect{
                when(it){
                    is ResultState.Loading->{
                        _isAvailable.value = AvailabilityResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _isAvailable.value = AvailabilityResultState(isAvailable = it.data)
                    }
                    is ResultState.Error->{
                        _isAvailable.value = AvailabilityResultState(error= it.message.toString())
                    }
                }
            }
        }
    }
}

data class LatLngResultState(

    val isLoading:Boolean = false,
    val locationAddress : UserLocationAddress?= null,
    val error :String = "",
)

data class AvailabilityResultState(

    val isAvailable:CheckAvailabilityDataModel? = null,
    val isLoading : Boolean = false,
    val error :String = "",
)