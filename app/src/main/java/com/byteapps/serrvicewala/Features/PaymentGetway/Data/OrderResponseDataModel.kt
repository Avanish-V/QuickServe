package com.byteapps.serrvicewala.Features.PaymentGetway.Data

import com.google.firebase.database.IgnoreExtraProperties
import com.squareup.moshi.Json



data class OrderResponseDataModel(
    @Json(name = "cart_details") val cartDetails: Any?, // Change the type to the actual type if known
    @Json(name = "cf_order_id") val cfOrderId: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "customer_details") val customerDetails: CustomerDetails,
    val entity: String,
    @Json(name = "order_amount") val orderAmount: Double,
    @Json(name = "order_currency") val orderCurrency: String,
    @Json(name = "order_expiry_time") val orderExpiryTime: String,
    @Json(name = "order_id") val orderId: String,
    @Json(name = "order_meta") val orderMeta: OrderMeta,
    @Json(name = "order_note") val orderNote: String?,
    @Json(name = "order_splits") val orderSplits: List<Any>, // Change the type to the actual type if known
    @Json(name = "order_status") val orderStatus: String,
    @Json(name = "order_tags") val orderTags: Any?, // Change the type to the actual type if known
    @Json(name = "payment_session_id") val paymentSessionId: String,
    @Json(name = "terminal_data") val terminalData: Any? // Change the type to the actual type if known
)

data class OrderMeta(
    @Json(name = "return_url") val returnUrl: String ?= null,
    @Json(name = "notify_url") val notifyUrl: String? = null, // Change the type to the actual type if known
    @Json(name = "payment_methods") val paymentMethods: Any? // Change the type to the actual type if known
)
