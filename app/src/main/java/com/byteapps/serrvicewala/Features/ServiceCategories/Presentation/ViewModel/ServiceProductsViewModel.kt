package com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceProductDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Domain.Repository.ServiceCategoryRepository
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceProductsViewModel @Inject constructor(private val serviceCategoryRepository: ServiceCategoryRepository): ViewModel() {

    private val _serviceProductsDataItem : MutableState<ServiceProductsListResultState> = mutableStateOf(
        ServiceProductsListResultState()
    )
    val serviceProductDataItem : State<ServiceProductsListResultState> = _serviceProductsDataItem



    fun getServiceProducts(categoryId:String){
        viewModelScope.launch {
            serviceCategoryRepository.getServices(categoryId).collect{
                when(it){
                    is ResultState.Loading->{
                        _serviceProductsDataItem.value = ServiceProductsListResultState(isLoading = true)
                    }
                    is ResultState.Success->{

                        _serviceProductsDataItem.value = ServiceProductsListResultState(serviceProductsList = it.data)
                    }
                    is ResultState.Error->{

                        _serviceProductsDataItem.value = ServiceProductsListResultState(error = it.message)
                    }
                }
            }
        }
    }



}

data class ServiceProductsListResultState(
    val serviceProductsList : List<ServiceProductDataModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String = ""
)