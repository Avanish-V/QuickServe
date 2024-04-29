package com.byteapps.serrvicewala.Features.Orders.data

import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceDetails
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon


data class OrdersDataModel(

    val orderId: String? = null,
    val userUUID: String,
    val serviceInfo: ServiceInfo,
    val priceTag: PriceDetails,
    val address: UserAddressDataModel,
    val dateTime: DateTime,
    val tracking: Tracking,
    var status: Status,
    val professionalID: String = "",
    val professionalDetail:ProfessionalDetails ?= null

) {

    constructor() : this(null, "", ServiceInfo("", ""), PriceDetails(), UserAddressDataModel(), DateTime("", ""), Tracking.PLACED, Status.ACTIVE)
}


data class ServiceInfo(

    val serviceTitle : String = "",
    val serviceId : String = "",
)

data class DateTime(

    val date : String = "",
    val time : String  = "",

)

enum class Status{

    ACTIVE, CANCELED, REFUNDED

}

enum class Tracking {

    PLACED, ASSIGNED, COMPLETED

}

data class ProfessionalDetails(

    val professionalName:String = "",
    val professionalImage:String = "",
    val rating : Int = 0,
    val count : Int = 0

)