package com.byteapps.serrvicewala.Features.PaymentGetway.Data

import com.squareup.moshi.Json

data class CreateOrderDataModel(

    @Json(name = "order_amount") val orderAmount: Double,
    @Json(name = "order_currency") val orderCurrency: String,
    @Json(name = "order_id") val orderId: String,
    @Json(name = "customer_details") val customerDetails: CustomerDetails,
   // @Json(name = "order_meta") val orderMeta: OrderMeta
)


data class CustomerDetails(
    @Json(name = "customer_id") val customerId: String,
    @Json(name = "customer_name") val customerName: String,
    @Json(name = "customer_email") val customerEmail: String,
    @Json(name = "customer_phone") val customerPhone: String
)

