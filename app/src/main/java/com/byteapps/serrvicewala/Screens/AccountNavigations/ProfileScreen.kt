package com.byteapps.karigar.presentation.Screens.AccountNavHost

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.UserProfile.UserProfileDataModel
import com.byteapps.serrvicewala.Features.UserProfile.UserProfileViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareUserProfileViewModel
import com.byteapps.serrvicewala.UIComponents.CommonInputField
import com.byteapps.serrvicewala.UIComponents.FullWidthButton
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    shareUserProfileViewModel : ShareUserProfileViewModel,
    userProfileViewModel: UserProfileViewModel
) {

    val scope = rememberCoroutineScope()

    var isLoading by remember {
        mutableStateOf(false)
    }

    val profileResult = userProfileViewModel.userProfileState.value

    profileResult.profileData?.let { shareUserProfileViewModel.getName(it.userName) }
    profileResult.profileData?.let { shareUserProfileViewModel.getEmail(it.userEmail) }
    profileResult.profileData?.let { shareUserProfileViewModel.getGender(it.userGender) }
    profileResult.profileData?.let { shareUserProfileViewModel.getMobile(it.userMobile) }


    val snackBarHostState = SnackbarHostState()

    LaunchedEffect(Unit){

        userProfileViewModel.getUserProfile()

    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                    }

                }
            )

        },
        snackbarHost = {
           SnackbarHost(hostState = snackBarHostState) {
               Snackbar(snackbarData = it)
           }
        }
    ) { paddingValues ->

        Column(
            Modifier
                .padding(paddingValues)
                .padding(all = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {



            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shareUserProfileViewModel.userName.collectAsState().value,
                onValueChange = {
                    shareUserProfileViewModel.getName(it)
                },
                label = { Text(text = "Name")},
                placeholder = {
                    Text(text = "Vishal", color = MaterialTheme.colorScheme.onSecondary)
                },

            )



            GenderSelection(
                selectedGender = shareUserProfileViewModel.userGender.collectAsState().value,
                onSelect ={ shareUserProfileViewModel.getGender(it)}
            )



            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shareUserProfileViewModel.userEmail.collectAsState().value,
                onValueChange = {
                    shareUserProfileViewModel.getEmail(it)
                },
                label = { Text(text = "Email")},
                placeholder = {
                    Text(text = "example@gmail.com", color = MaterialTheme.colorScheme.onSecondary)
                },

            )

            

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shareUserProfileViewModel.userMobile.collectAsState().value,
                onValueChange = {
                    shareUserProfileViewModel.getMobile(it)
                },
                label = { Text(text = "Mobile")},
                placeholder = {
                    Text(text = "8787XXXX61", color = MaterialTheme.colorScheme.onSecondary)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                prefix = {
                    Text(text = "+91")
                }

            )


            FullWidthButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(48.dp),
                text = "Submit",
                textColor = Color.White,
                color = MaterialTheme.colorScheme.primary,
                enabled = true
            ) {

                scope.launch {

                    userProfileViewModel.setProfile(
                        UserProfileDataModel(
                            userName = shareUserProfileViewModel.userName.value,
                            userEmail = shareUserProfileViewModel.userEmail.value,
                            userGender = shareUserProfileViewModel.userGender.value,
                            userMobile = shareUserProfileViewModel.userMobile.value
                        )
                    ).collect{
                        when(it){
                            is ResultState.Loading->{
                                isLoading = true
                            }
                            is ResultState.Success->{
                                isLoading = false
                                snackBarHostState.showSnackbar(it.data)
                            }
                            is ResultState.Error->{
                                isLoading = false
                            }

                            else -> {}
                        }
                    }
                }


            }

        }

        if (isLoading){
            LoadingScreen()
        }

    }
}

@Composable
fun GenderSelection(selectedGender:String,onSelect:(String)->Unit) {


    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){

        items(genderList){

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Card(
                    onClick = { onSelect(it.text) },
                    modifier = Modifier.size(64.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedGender == it.text) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
                        contentColor = if (selectedGender == it.text) Color.White else MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = it.icon),
                            contentDescription = ""
                        )
                    }
                }
                Text(
                    text = it.text,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }


        }
    }


}

data class Gender(
    val text: String,
    val icon: Int
)

val genderList = mutableListOf<Gender>(
    Gender("Male", R.drawable.male),
    Gender("Female", R.drawable.female),
)

