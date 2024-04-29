package com.byteapps.wiseschool.GeoFencing

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LatLngDataModel(
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,

){
    constructor() : this(0.0, 0.0)
}
data class GeoStatusDataModel(
    val status : Boolean,
    val radius : Int,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
)

