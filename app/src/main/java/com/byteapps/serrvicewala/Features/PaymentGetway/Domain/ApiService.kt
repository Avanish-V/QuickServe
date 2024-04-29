package com.byteapps.serrvicewala.Features.PaymentGetway.Domain

import com.byteapps.serrvicewala.Features.PaymentGetway.Data.CreateOrderDataModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    companion object{
        const val BASE_URL = " https://sandbox.cashfree.com/"
    }

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "x-api-version: 2023-08-01",
        "X-Client-Id: 26491391629f6d58913eed3248319462",
        "X-Client-Secret: f3ee7fa0dfcb44f4a8397c163a6b6490960a1682"
    )
    @POST("pg/orders")
    fun createOrder(@Body orderRequest: CreateOrderDataModel):Call<ResponseBody>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "x-api-version: 2023-08-01",
        "X-Client-Id: 26491391629f6d58913eed3248319462",
        "X-Client-Secret: f3ee7fa0dfcb44f4a8397c163a6b6490960a1682"
    )
    @GET("pg/orders/{order_id}")
    fun getOrderStatus(@Path("order_id") orderId: String):Call<ResponseBody>
}