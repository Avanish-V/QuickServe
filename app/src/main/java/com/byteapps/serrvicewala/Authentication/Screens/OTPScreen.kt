package com.byteapps.serrvicewala.Authentication.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.Utils.NavigationRoutes.AuthenticationRoutes
import com.byteapps.serrvicewala.Utils.NavigationRoutes.MainRootRoute
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteappstudio.b2ccart.Authentications.AuthViewModel
import com.byteappstudio.blooddonate.Authentications.OtpView
import kotlinx.coroutines.launch

@Composable
fun OTPScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel
) {

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.None,)
        ){
            append("Don’t receive OTP code?")
        }
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.None, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        ){
            append(" Resend" )
        }

    }

    var otp by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.phonelogin),
                contentDescription = null
            )

            Column (
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ){


                Text(text = "OTP Verification", style = MaterialTheme.typography.displayLarge)

                Text(text = "Enter OTP code sent to +91${authViewModel.phoneNumber.value}",style = MaterialTheme.typography.displayMedium)

                OtpView(
                    onOtpTextChange = {otp = it},
                    otpText = otp,
                    charColor = MaterialTheme.colorScheme.primary
                )

                Text(text = annotatedString, style = MaterialTheme.typography.labelMedium, modifier = Modifier.clickable {
                    navHostController.navigate(AuthenticationRoutes.PHONE_NUMBER)
                })
            }


        }

        Button(
            onClick = {

                scope.launch {
                    authViewModel.signInWithCredential(code = otp)
                        .collect{
                            when(it){
                                is ResultState.Loading->{
                                    isLoading = true
                                }
                                is ResultState.Success->{
                                    navHostController.navigate(MainRootRoute.MAIN_ROUTE)
                                }
                                is ResultState.Error->{
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        it.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
                .height(48.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Verify", color = MaterialTheme.colorScheme.background)

        }

    }

    if( isLoading) LoadingScreen()

}