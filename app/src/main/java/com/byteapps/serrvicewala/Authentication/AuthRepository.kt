package com.byteappstudio.b2ccart.Authentications

import android.app.Activity
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createUserWithPhone(phone:String, activity: Activity) : Flow<ResultState<String>>

    fun signWithCredential(otp:String) : Flow<ResultState<String>>

}