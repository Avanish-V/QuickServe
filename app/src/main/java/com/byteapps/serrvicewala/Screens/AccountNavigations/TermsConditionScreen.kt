package com.byteapps.karigar.presentation.Screens.AccountNavHost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsConditionScreen(navHostController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Terms & Conditions") },
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

    }

}