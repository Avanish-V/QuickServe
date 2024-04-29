package com.byteapps.serrvicewala.LocationPermission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.byteapps.wiseschool.GeoFencing.Permission.PermissionViewModel
import java.security.Permissions

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentDeclined:Boolean,
    onDismiss:()->Unit,
    onOkClick:()->Unit,
    onGoToAppsSettingsClick:()->Unit,
    modifier:Modifier = Modifier
) {


    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
           TextButton(
               onClick = {
                   if (isPermanentDeclined) {
                       onGoToAppsSettingsClick()
                   } else {
                       onOkClick()
                   }
               }
           ) {
               Text(text = if(isPermanentDeclined) {
                   "Grant permission"

               } else {
                   "OK"
               },)
           }

        },
        title = {
            Text(text = "Permission required")
        },
        text = {
           Text(
               text = permissionTextProvider.getDescription(isPermanentDeclined = isPermanentDeclined)
           )
        },

    )

}

interface PermissionTextProvider{
    fun getDescription(isPermanentDeclined: Boolean):String

}

class locationPermissionText:PermissionTextProvider{
    override fun getDescription(isPermanentDeclined: Boolean): String {
       return if (isPermanentDeclined){
           "It seems you permanently declined camera permission."+
                   "You can go to the app settings to grant it."
       }else{
           "This app needs to access your location."
       }
    }

}


@Composable
fun StateDialog(dialogQueue : SnapshotStateList<String>,permissionViewModel:PermissionViewModel,context: Context) {
    dialogQueue.forEach {permission->

        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    locationPermissionText()
                }

                else -> return@forEach
            },
            isPermanentDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = {
                permissionViewModel.dismissDialog()
            },
            onOkClick = {
                permissionViewModel.dismissDialog()
            },
            onGoToAppsSettingsClick = {

                permissionViewModel.openAppSettings(context)

            }

        )
    }
}

