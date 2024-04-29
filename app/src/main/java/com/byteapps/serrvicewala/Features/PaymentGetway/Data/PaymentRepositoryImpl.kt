package com.byteapps.serrvicewala.Features.PaymentGetway.Data

import android.util.Log
import com.byteapps.serrvicewala.Features.PaymentGetway.Domain.ApiService
import com.byteapps.serrvicewala.Features.PaymentGetway.Domain.PaymentRepository
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.serrvicewala.Utils.ResultState.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val apiService: ApiService):
    PaymentRepository {

    override fun createOrder(createOrderDataModel: CreateOrderDataModel): Flow<ResultState<OrderResponseDataModel?>> = callbackFlow {

        trySend(Loading)


        val service = apiService.createOrder(createOrderDataModel)

        service.enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {

                    val responseBody = response.body()?.string()

                    if (!responseBody.isNullOrEmpty()) {

                        val orderResponseDataModel = parseResponse(responseBody)

                        trySend(ResultState.Success(orderResponseDataModel))

                    } else {

                        trySend(ResultState.Error(response.code().toString()))
                    }

                } else {
                    trySend(ResultState.Error(response.code().toString()))
                }
                close()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

               trySend(ResultState.Error(t.message.toString()))


            }
        })

       awaitClose {
           close()
       }

    }

    override fun fetchOrderDetails(orderId:String): Flow<ResultState<OrderResponseDataModel?>> = callbackFlow {


        trySend(Loading)


        val service = apiService.getOrderStatus(orderId)

        service.enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {

                    val responseBody = response.body()?.string()

                    if (!responseBody.isNullOrEmpty()) {

                        val orderResponseDataModel = parseResponse(responseBody)

                        trySend(ResultState.Success(orderResponseDataModel))

                    } else {

                        trySend(ResultState.Error(response.code().toString()))
                    }

                } else {
                    trySend(ResultState.Error(response.code().toString()))
                }
                close()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                trySend(ResultState.Error(t.message.toString()))


            }
        })

        awaitClose {
            close()
        }

    }

}




private fun parseResponse(responseBody: String): OrderResponseDataModel? {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = moshi.adapter(OrderResponseDataModel::class.java)
    val orderResponse = adapter.fromJson(responseBody)


    return orderResponse
}
