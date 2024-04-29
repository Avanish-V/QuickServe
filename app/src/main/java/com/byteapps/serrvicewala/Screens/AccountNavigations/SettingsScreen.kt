package com.byteapps.karigar.presentation.Screens.AccountNavHost

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navHostController: NavHostController) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Setting") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                    }

                }
            )

        }
    ) { paddingValues ->

        var switch by rememberSaveable {
            mutableStateOf(false)
        }
        Column (Modifier.padding(paddingValues)){

            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                Column (Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)){
                    Text(
                        text = "Notification",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "You agree to receive notifications about\nnew upcoming.",
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 13.sp,
                        lineHeight = 14.sp
                    )
                }


                Switch(
                    checked = switch,
                    onCheckedChange ={switch = it},
                    colors = SwitchDefaults.colors(
                        uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White,

                    ),
                    modifier = Modifier.height(13.dp),


                )

            }

        }


    }
}