package com.byteapps.karigar.presentation.Screens.AccountNavHost

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.rotationMatrix
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenter(navHostController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Help Center") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }

                }
            )

        }
    ) { paddingValues ->

        LazyColumn(Modifier.padding(paddingValues), contentPadding = PaddingValues(13.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {

            items(questionsList){
                HelpSingleRow(title = it.title, description = it.description)
            }

            item {

                Column(modifier = Modifier.padding(top = 40.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    Text(text = "Contact Support", style = MaterialTheme.typography.displayMedium)

                    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)){
                        Icon(painter = painterResource(id = R.drawable.user_headset), contentDescription = "")
                        Text(text = "+918787040661 ( 09:00 AM - 05:00 PM )", style = MaterialTheme.typography.displaySmall)
                    }

                }
            }

        }
    }

}


@Composable
fun HelpSingleRow(title: String,description: String) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    val rotations by remember {
        derivedStateOf { if (isVisible) 45f else 90f }
    }

    Column(
        Modifier.background(
            color = MaterialTheme.colorScheme.onTertiary,
            shape = RoundedCornerShape(5.dp)
        )
    ) {

        Row(

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 30.dp, top = 10.dp, bottom = 10.dp),
                color = if (isVisible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                text = title
            )

            OutlinedIconButton(

                modifier = Modifier.focusable(false),
                onClick = { isVisible = !isVisible },
                border = BorderStroke(width = 0.dp, color = Color.Transparent)
            ) {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    Icon(
                        modifier = Modifier.rotate(rotations),
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = if (isVisible) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }

            }
        }
        AnimatedVisibility(visible = isVisible) {
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 14.sp,
                text = description
            )
        }

    }


}


data class HelpCenter(
    val title:String = "",
    val description:String = "",
)


val questionsList = mutableListOf<HelpCenter>(

    HelpCenter(
        title = "How i can get my refund?",
        description = "After cancel your order, refund will be initiated automatically and refund will receive in your original payment method."
    ),
    HelpCenter(
        title = "How i can delete my account and data?",
        description = "Yes, you can delete your account and data go to account -> Setting now click on delete account, your account and data will be deleted permanently."
    )


)