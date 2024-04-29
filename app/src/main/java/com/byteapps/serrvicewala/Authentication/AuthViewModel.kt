package com.byteappstudio.b2ccart.Authentications

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val repo : AuthRepository): ViewModel() {


    var phoneNumber : MutableState<String> = mutableStateOf("")
    val number : State<String> = phoneNumber

    fun createUserWithPhone(phone:String, activity: Activity) = repo.createUserWithPhone(phone,activity)

    fun signInWithCredential(code:String) = repo.signWithCredential(code)

    fun phoneNumber(phone:String){
        phoneNumber.value = phone
    }


}