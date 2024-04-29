
package com.byteapps.serrvicewala.Features.PriceCalculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceCalculationViewModel @Inject constructor(private val priceRepository: PriceRepository): ViewModel()  {


    private val _priceWithCoupon = MutableStateFlow<PriceTagResultState>(PriceTagResultState())
    val priceWithCoupon: StateFlow<PriceTagResultState> get() = _priceWithCoupon



    fun setPriceTagWithCoupon(priceTagWithCoupon: PriceDetails){

        viewModelScope.launch {

            priceRepository.setPriceWithCoupon(priceTagWithCoupon).collect{
                when(it){
                    is ResultState.Loading->{
                        _priceWithCoupon.value = PriceTagResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _priceWithCoupon.value = PriceTagResultState(calculatedPriceWithCoupon = it.data)
                    }
                    is ResultState.Error->{
                        _priceWithCoupon.value = PriceTagResultState(error = it.message)
                    }
                }
            }
        }
    }


}



data class PriceTagResultState(
    val calculatedPriceWithCoupon: PriceDetails? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)