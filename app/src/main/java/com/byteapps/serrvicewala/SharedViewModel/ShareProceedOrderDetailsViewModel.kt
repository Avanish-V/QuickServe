package com.byteapps.serrvicewala.SharedViewModel

import androidx.lifecycle.ViewModel
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShareProceedOrderDetailsViewModel : ViewModel() {



    private val _address = MutableStateFlow<UserAddressDataModel>(UserAddressDataModel())
    val address: StateFlow<UserAddressDataModel> get() = _address


    private val _quantity = MutableStateFlow<Int>(1)
    val quantity: StateFlow<Int> get() = _quantity

    private val _date = MutableStateFlow<String>("")
    val date: StateFlow<String> get() = _date

    private val _time = MutableStateFlow<String>("")
    val time: StateFlow<String> get() = _time





    fun getAddress(address: UserAddressDataModel){
        _address.value = address
    }

    fun getDate(date:String){
        _date.value = date
    }

    fun getTime(time:String){
        _time.value = time
    }


    fun setQuantity(quantity:Int){
        _quantity.value = quantity
    }









}