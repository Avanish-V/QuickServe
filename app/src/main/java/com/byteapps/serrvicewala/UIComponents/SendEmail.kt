package com.byteapps.serrvicewala.UIComponents

import android.content.Context
import android.content.Intent
import android.net.Uri

fun sendEmailSingleRecipient(context: Context, recipient: String) {

    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.setData(Uri.parse("mailto:$recipient"))
    context.startActivity(emailIntent)
}