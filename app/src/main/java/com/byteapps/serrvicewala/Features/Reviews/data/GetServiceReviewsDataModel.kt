package com.byteapps.serrvicewala.Features.Reviews.data

data class GetServiceReviewsDataModel(

    val professionalId : String = "",
    val comment : String = "",
    val rating : Int = 0,
    val date : String = "",
    val reviewBy : String = ""

)