package com.byteapps.serrvicewala.SharedViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceProductDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShareOrderDetailsViewModel : ViewModel() {

    val _orderDetails = MutableStateFlow<OrdersDataModel?>(null)
    val orderDetails: StateFlow<OrdersDataModel?> get() = _orderDetails


    fun setOrderDetailsData(ordersDataModel: OrdersDataModel){
        _orderDetails.value = ordersDataModel
    }



}