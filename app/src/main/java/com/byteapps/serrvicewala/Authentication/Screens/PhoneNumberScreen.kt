package com.byteapps.serrvicewala.Authentication.Screens

import android.app.Activity
import android.inputmethodservice.Keyboard
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.Utils.NavigationRoutes.AuthenticationRoutes
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteappstudio.b2ccart.Authentications.AuthViewModel
import kotlinx.coroutines.launch


@Composable

fun PhoneNumberScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var isInVailid by rememberSaveable {
        mutableStateOf(false)
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Column(Modifier.fillMaxWidth().weight(1f)) {


            Image(
                painter = painterResource(id = R.drawable.phonelogin),
                contentDescription = null,
                Modifier.size(300.dp)
            )

            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),

                ) {

                Text(text = "SignIn", style = MaterialTheme.typography.displayLarge)

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text(text = "+91")},
                    suffix = { Text(text = "${phoneNumber.length}/10", color = if (isInVailid ) Color.Red else Color.Black)},
                    placeholder = { Text(text = "Enter Mobile ", color = Color.LightGray)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {Keyboard.KEYCODE_CANCEL})
                )

                Button(
                    onClick = {


                        if (phoneNumber.length < 10 || phoneNumber.length > 10){

                            isInVailid = true

                        }else{
                            scope.launch {

                                authViewModel.createUserWithPhone(phoneNumber, context as Activity)
                                    .collect{
                                        when(it){
                                            is ResultState.Loading->{
                                                isLoading = true
                                            }
                                            is ResultState.Success->{

                                                authViewModel.phoneNumber(phoneNumber)

                                                navHostController.navigate(
                                                    AuthenticationRoutes.OPT,
                                                )

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
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Get OTP", color = MaterialTheme.colorScheme.background)

                }

            }

        }


        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(textDecoration = TextDecoration.None,)
            ){
                append("I agree with ")
            }
            withStyle(
                style = SpanStyle(textDecoration = TextDecoration.None, color = MaterialTheme.colorScheme.primary)
            ){
                append(" Terms")
            }
            withStyle(
                style = SpanStyle(textDecoration = TextDecoration.None, color = MaterialTheme.colorScheme.onPrimary)
            ){
                append(" &")
            }
            withStyle(
                style = SpanStyle(textDecoration = TextDecoration.None, color = MaterialTheme.colorScheme.primary)
            ){
                append(" Conditions")
            }
        }

        Text(text = annotatedString, style = MaterialTheme.typography.labelMedium)

    }

    if (isLoading) LoadingScreen()

}