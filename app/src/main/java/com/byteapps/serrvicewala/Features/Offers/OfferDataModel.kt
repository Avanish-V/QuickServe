package com.byteapps.serrvicewala.Features.Offers


data class OfferDataModel(

    val offerId:String = "",
    val offerTitle:String = "",
    val offerDesc:String = "",
    val offerCode:String = "",
    val offerImage:String = "",
    val offerDiscount:Int = 0,
    val offerTAG:String = "",
    val offerExp:String = ""

)
