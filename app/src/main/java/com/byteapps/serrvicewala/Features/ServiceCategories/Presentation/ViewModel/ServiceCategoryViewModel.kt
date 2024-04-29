package com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceCategoryDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Domain.Repository.ServiceCategoryRepository
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceCategoryViewModel @Inject constructor(private val serviceCategoryRepository: ServiceCategoryRepository) : ViewModel() {

    private val _serviceCategoryDataItem : MutableState<ServiceCategoryResultState> = mutableStateOf(
        ServiceCategoryResultState()
    )
    val serviceCategoryDataItem : State<ServiceCategoryResultState> = _serviceCategoryDataItem

    init {
        viewModelScope.launch {
            serviceCategoryRepository.getServiceCategories().collect{
                when(it){
                    is ResultState.Loading->{
                        _serviceCategoryDataItem.value = ServiceCategoryResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _serviceCategoryDataItem.value = ServiceCategoryResultState(serviceCategoryList = it.data)
                    }
                    is ResultState.Error->{
                        _serviceCategoryDataItem.value = ServiceCategoryResultState(error = it.message)
                    }
                }
            }
        }
    }

}

data class ServiceCategoryResultState(

    val serviceCategoryList : List<ServiceCategoryDataModel> = emptyList(),
    val isLoading : Boolean = false,
    val error:String = ""

)