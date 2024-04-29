package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.byteapps.QuickServe.R


@Composable
fun ShowRating(modifier: Modifier = Modifier, index: Int) {

    var ratingState by remember {
        mutableStateOf(index)
    }


    Row() {

        for (index in 1..5) {

            Icon(
                painter = if (index <= ratingState) painterResource(id = R.drawable.star__1_) else painterResource(
                    id = R.drawable.star
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .padding(horizontal = 3.dp),
                tint = MaterialTheme.colorScheme.secondary

            )

        }
    }


}