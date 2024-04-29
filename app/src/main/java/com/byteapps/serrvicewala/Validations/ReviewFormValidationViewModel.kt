package com.byteapps.serrvicewala.Validations

import androidx.lifecycle.ViewModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewFormValidationViewModel:ViewModel() {


    var _reviewText = MutableStateFlow<String>("")
    val reviewText1: StateFlow<String> get() = _reviewText

    val _reviewCount = MutableStateFlow<Int>(0)
    val reviewCount: StateFlow<Int> get() = _reviewCount

    fun isValidated(reviewText:String,reviewCount:Int):Boolean{
        return reviewText.isNotEmpty() && reviewCount != 0
    }

}