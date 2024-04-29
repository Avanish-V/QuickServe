package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import com.byteapps.serrvicewala.ui.theme.MidTransparent

@Composable
fun LoadingScreen() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MidTransparent), contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round
        )
    }

}

@Composable
fun StatusScreen(text:String) {

    Box(modifier = Modifier.fillMaxSize().background(color = MidTransparent), contentAlignment = Alignment.Center){
       Text(text = text)
    }

}