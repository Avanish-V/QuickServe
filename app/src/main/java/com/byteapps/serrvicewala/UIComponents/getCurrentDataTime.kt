package com.byteapps.serrvicewala.UIComponents

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTimeDate():String{
    return formatTimestamp(getCurrentTimestamp())
}


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTimestamp(): Long {
    return Instant.now().toEpochMilli()
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    val instant = Instant.ofEpochMilli(timestamp)
    val formattedDate = instant.atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    return formattedDate
}