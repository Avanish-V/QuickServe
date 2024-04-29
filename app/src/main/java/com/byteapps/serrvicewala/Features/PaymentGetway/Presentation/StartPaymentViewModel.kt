package com.byteapps.serrvicewala.Features.PaymentGetway.Presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Address.AddressResultState
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.CreateOrderDataModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.OrderResponseDataModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Domain.PaymentRepository
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class StartPaymentViewModel @Inject constructor(private val paymentRepository: PaymentRepository) : ViewModel(){

    fun createOrder(createOrderDataModel: CreateOrderDataModel) = paymentRepository.createOrder(createOrderDataModel)


    private val _orderStatusResult = MutableStateFlow<OrderStatusResultState>(OrderStatusResultState())
    val orderStatusResult: StateFlow<OrderStatusResultState> get() = _orderStatusResult

    fun fetchOrderStatus(orderId:String){
        viewModelScope.launch {
            paymentRepository.fetchOrderDetails(orderId).collect{

                when(it){
                    is ResultState.Loading->{

                        _orderStatusResult.value = OrderStatusResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _orderStatusResult.value = OrderStatusResultState(transactionStatus = it.data)

                    }
                    is ResultState.Error->{
                        _orderStatusResult.value = OrderStatusResultState(error = it.message)

                    }
                }

            }
        }
    }

}

data class OrderStatusResultState(

    var transactionStatus: OrderResponseDataModel? = null,
    val isLoading:Boolean = false,
    val error:String = ""
)