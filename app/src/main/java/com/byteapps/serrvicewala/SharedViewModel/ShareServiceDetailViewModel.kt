package com.byteapps.serrvicewala.SharedViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceProductDataModel

class ShareServiceDetailViewModel : ViewModel() {

    private val _serviceDetails : MutableState<ServiceProductDataModel> = mutableStateOf(
        ServiceProductDataModel()
    )
    val serviceDetails:State<ServiceProductDataModel> = _serviceDetails

    fun setServiceDetails(serviceProductDataModel: ServiceProductDataModel){
        _serviceDetails.value = serviceProductDataModel
    }

}