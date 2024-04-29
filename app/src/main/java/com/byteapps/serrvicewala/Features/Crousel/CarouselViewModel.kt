package com.byteapps.serrvicewala.Features.Crousel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarouselViewModel @Inject constructor(private val carouselRepository: CarouselRepository) : ViewModel() {

    private val _carouselResults : MutableState<CarouselResultState> = mutableStateOf(CarouselResultState())
    val carouselResults : State<CarouselResultState> = _carouselResults

    init {
        viewModelScope.launch {
            carouselRepository.getCarousel().collect{
                when(it){
                    is ResultState.Loading->{
                        _carouselResults.value = CarouselResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _carouselResults.value = CarouselResultState(carouselList = it.data)
                    }
                    is ResultState.Error->{
                        _carouselResults.value = CarouselResultState(error = it.message)
                    }
                }
            }
        }
    }

}

data class CarouselResultState(

    val carouselList : List<CarouselDataModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String = ""

)