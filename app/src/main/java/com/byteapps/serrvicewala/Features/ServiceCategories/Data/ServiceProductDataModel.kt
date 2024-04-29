package com.byteapps.serrvicewala.Features.ServiceCategories.Data

import com.google.firebase.database.IgnoreExtraProperties
import java.sql.Timestamp

@IgnoreExtraProperties
data class ServiceProductDataModel(

    val serviceTitle:String = "",
    val images:List<String>,
    val serviceId:String="",
    val serviceTAG:String = "",
    val price:Int = 0,
    val tax:Int = 0,
    val rating : Rating,
    val description : List<Description>

){
    // Add a no-argument constructor
    constructor() : this("", listOf(), "", "",0,0, Rating(), listOf())
}

data class Rating(
    val rating : Int = 0,
    val count : Int = 0
)

data class Coupon(

    val offerTitle: String? = null,
    val couponCode: String? = null,
    val discount: Int? = null,

)
data class Description(
    val title :String = "",
    val comment : String = ""
)

data class User(
    val userName:String = "",
    val userImage:String  = "",
    val userId : String = ""
)

data class Reviews(
    val serviceId: String = "",
    val userId:String = "",
    val professionId : String = "",
    val description : String = "",
    val rating : Int = 0,
    val timestamp : Timestamp,

)
data class Address(
    val userName : String = "",
    val mobile : String = "",
    val address : String = "",
    val state : String = "",
    val pinCode : String = "",

)


