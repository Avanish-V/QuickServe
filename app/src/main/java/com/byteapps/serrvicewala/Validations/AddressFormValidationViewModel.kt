package com.byteapps.serrvicewala.Validations

import androidx.lifecycle.ViewModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddressFormValidationViewModel:ViewModel() {


    var _name = MutableStateFlow<String>("")
    val name: StateFlow<String> get() = _name

    var _mobile = MutableStateFlow<String>("")
    val mobile: StateFlow<String> get() = _mobile

    var _pinCode = MutableStateFlow<String>("")
    val pinCode: StateFlow<String> get() = _pinCode

    var _state = MutableStateFlow<String>("")
    val state: StateFlow<String> get() = _state

    var _city = MutableStateFlow<String>("")
    val city: StateFlow<String> get() = _city

    var _building = MutableStateFlow<String>("")
    val building: StateFlow<String> get() = _building

    var _area = MutableStateFlow<String>("")
    val area: StateFlow<String> get() = _area

    val _type = MutableStateFlow<String>("")
    val type: StateFlow<String> get() = _type

    fun isValidated(
        name:String,
        mobile:String,
        pinCode:String,
        state:String,
        city:String,
        building:String,
        area:String,
        type:String,
    ):Boolean{
        return name.isNotEmpty() &&
                mobile.isNotEmpty() &&
                pinCode.isNotEmpty() &&
                state.isNotEmpty() &&
                city.isNotEmpty() &&
                building.isNotEmpty() &&
                area.isNotEmpty() &&
                type.isNotEmpty()
    }

}