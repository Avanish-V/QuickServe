package com.byteapps.serrvicewala.Features.Orders

import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface OrdersRepository  {

    fun setOrder(ordersDataModel: OrdersDataModel):Flow<ResultState<OrdersDataModel>>

    fun getOrder():Flow<ResultState<List<OrdersDataModel>>>

    fun cancelOrder(orderId: String,status: Status) : Flow<ResultState<Status>>

    fun setReview(reviewDataModel: ReviewDataModel):Flow<ResultState<ReviewDataModel>>

    fun getReview(orderId:String):Flow<ResultState<Map<String,String>>>

}