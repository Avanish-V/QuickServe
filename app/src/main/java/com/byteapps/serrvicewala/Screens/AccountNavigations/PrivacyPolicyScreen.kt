package com.byteapps.karigar.presentation.Screens.AccountNavHost

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Privacy Policy") },
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

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        // Enable JavaScript (optional)
                        settings.javaScriptEnabled = true
                        // Set up a WebViewClient to handle page loading
                        webViewClient = WebViewClient()
                        // Load the HTML file from assets
                        loadUrl("file:///android_asset/PrivecyPolicy.html")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )


        }

    }

}

