package com.byteapps.serrvicewala.Features.PaymentGetway.Domain

import com.byteapps.serrvicewala.Features.PaymentGetway.Data.CreateOrderDataModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.OrderResponseDataModel
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface PaymentRepository {

    fun createOrder(createOrderDataModel: CreateOrderDataModel) : Flow<ResultState<OrderResponseDataModel?>>

    fun fetchOrderDetails(orderId:String) : Flow<ResultState<OrderResponseDataModel?>>
}