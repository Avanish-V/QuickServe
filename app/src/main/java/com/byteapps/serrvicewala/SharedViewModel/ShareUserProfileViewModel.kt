package com.byteapps.serrvicewala.SharedViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShareUserProfileViewModel : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> get() = _userName

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail: StateFlow<String> get() = _userEmail

    private val _userGender = MutableStateFlow<String>("")
    val userGender: StateFlow<String> get() = _userGender

    private val _userMobile = MutableStateFlow<String>("")
    val userMobile: StateFlow<String> get() = _userMobile


    fun getName(name:String):Boolean{
        _userName.value = name
        return  (userName.value.isEmpty())
    }
    fun getEmail(email:String):Boolean{
        _userEmail.value = email
        return  (userEmail.value.isEmpty())
    }
    fun getGender(gender:String):Boolean{
        _userGender.value = gender
        return  (userGender.value.isEmpty())
    }
    fun getMobile(mobile:String):Boolean{
        _userMobile.value = mobile
        return  (userMobile.value.isEmpty())
    }



}