package com.byteapps.serrvicewala.Features.Orders.data

data class ReviewDataModel(


    val professionalId : String = "",
    val serviceId : String = "",
    val orderId : String = "",
    val comment : String = "",
    val rating : Int = 0,
    val date : String = "",
    val reviewBy : String = ""

)