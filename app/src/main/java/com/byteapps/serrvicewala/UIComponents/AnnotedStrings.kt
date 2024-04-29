package com.byteapps.serrvicewala.UIComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun SpannableOfferText(

    offerTitle: String?,
    couponCode: String?

) {
    var maxlines by remember {
        mutableStateOf(3)
    }

    val annotatedString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("$offerTitle")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Black
            )
        ) {
            append(" $couponCode")
        }

    }

    Text(
        text = annotatedString,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,

    )

}
