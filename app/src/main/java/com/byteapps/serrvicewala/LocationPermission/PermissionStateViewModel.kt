package com.byteapps.serrvicewala.LocationPermission

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PermissionStateViewModel : ViewModel(){

    fun setPermissionGranted(managedActivityResultLauncher: ManagedActivityResultLauncher<String,Boolean>) {
        managedActivityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}