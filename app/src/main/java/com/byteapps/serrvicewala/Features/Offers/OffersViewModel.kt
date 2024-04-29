package com.byteapps.serrvicewala.Features.Offers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.Orders.OrdersResultState
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(private val offerRepository: OfferRepository):ViewModel() {

    private val _offerResult : MutableState<OfferResultState> = mutableStateOf(OfferResultState())
    val offerResult : androidx.compose.runtime.State<OfferResultState> = _offerResult

    private val _offerResultById : MutableState<OfferResultState> = mutableStateOf(OfferResultState())
    val offerResultById : androidx.compose.runtime.State<OfferResultState> = _offerResultById

    private fun getOffersResult(){
        viewModelScope.launch {

            offerRepository.getOffers().collect{
                when(it){
                    is ResultState.Loading->{
                        _offerResult.value = OfferResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _offerResult.value = OfferResultState(offerList = it.data)
                    }
                    is ResultState.Error->{
                        _offerResult.value = OfferResultState(error = it.message)
                    }
                }
            }
        }
    }

    fun getOffersResultById(offerTag:String){
        viewModelScope.launch {

            offerRepository.getOffers().collect{
                when(it){
                    is ResultState.Loading->{
                        _offerResultById.value = OfferResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _offerResultById.value = OfferResultState(offerList = it.data)
                    }
                    is ResultState.Error->{
                        _offerResultById.value = OfferResultState(error = it.message)
                    }
                }
            }
        }
    }

    init {
        getOffersResult()
    }

}

data class OfferResultState(
    val offerList:List<OfferDataModel> = emptyList(),
    val isLoading :Boolean = false,
    val error:String = ""
)