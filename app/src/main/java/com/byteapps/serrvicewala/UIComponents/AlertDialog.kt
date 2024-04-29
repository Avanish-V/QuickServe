package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byteapps.QuickServe.R


@Composable
fun MyAlertDialog(onOuterClick:()->Unit,onDismissClick:()->Unit,onConfirmClick:()->Unit,isLoading:Boolean) {

    AlertDialog(
        onDismissRequest = {
            onOuterClick()
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {onConfirmClick() },
                shape = RoundedCornerShape(5.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    if(isLoading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp,
                            strokeCap = StrokeCap.Round
                        )
                    }else{
                        Text(text = "Yes", color = Color.White)
                    }
                }

            }
        },
        dismissButton = {

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDismissClick() },
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(width = 1.dp,MaterialTheme.colorScheme.onTertiary)
            ) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        icon = {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onTertiary),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.round_warning_amber_24),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        title = {
                Text(text = "Are you sure ?", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
               Text(text = "Cancel if you think you have clicked by mistake",fontSize = 12.sp)
        },
        shape = RoundedCornerShape(10.dp),
        containerColor = Color.White
    )

}