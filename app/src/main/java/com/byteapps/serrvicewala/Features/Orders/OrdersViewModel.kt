package com.byteapps.serrvicewala.Features.Orders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersRepository: OrdersRepository): ViewModel() {

    fun setOrder(ordersDataModel: OrdersDataModel) = ordersRepository.setOrder(ordersDataModel)

    fun setReview(reviewDataModel: ReviewDataModel) = ordersRepository.setReview(reviewDataModel)

    fun cancelOrder(orderId : String,status: Status) = ordersRepository.cancelOrder(orderId,status)

    private val _ordersResults : MutableState<OrdersResultState> = mutableStateOf(OrdersResultState())
    val orderResult : State<OrdersResultState> = _ordersResults


   fun  getOrdersList(){

       viewModelScope.launch {
           ordersRepository.getOrder().collect{
               when(it){
                   is ResultState.Loading->{
                       _ordersResults.value = OrdersResultState(isLoading = true)
                   }
                   is ResultState.Success->{
                       _ordersResults.value = OrdersResultState(ordersList = it.data)
                   }
                   is ResultState.Error->{
                       _ordersResults.value = OrdersResultState(error = it.message)

                   }
               }
           }
       }
    }


}

data class OrdersResultState(

    val ordersList:List<OrdersDataModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String = ""
)