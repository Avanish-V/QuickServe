package com.byteapps.serrvicewala.Features.Reviews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Reviews.data.GetServiceReviewsDataModel
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceReviewsViewModel @Inject constructor(private val ordersRepository: ServiceReviewsRepository): ViewModel() {


    private val _reviewsResults : MutableState<ServiceReviewsResultState> = mutableStateOf(
        ServiceReviewsResultState()
    )
    val reviewsResults : State<ServiceReviewsResultState> = _reviewsResults


   fun  getServiceReviews(serviceId:String){

       viewModelScope.launch {
           ordersRepository.getServiceReviews(serviceId).collect{
               when(it){
                   is ResultState.Loading->{
                       _reviewsResults.value = ServiceReviewsResultState(isLoading = true)
                   }
                   is ResultState.Success->{
                       _reviewsResults.value = ServiceReviewsResultState(serviceReviewsList = it.data)
                   }
                   is ResultState.Error->{
                       _reviewsResults.value = ServiceReviewsResultState(error = it.message)

                   }
               }
           }
       }
    }


}

data class ServiceReviewsResultState(

    val serviceReviewsList:List<GetServiceReviewsDataModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String = ""
)