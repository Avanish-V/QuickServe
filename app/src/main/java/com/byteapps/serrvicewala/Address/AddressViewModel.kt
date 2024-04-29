package com.byteapps.serrvicewala.Address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Address
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val addressRepository: AddressRepository): ViewModel() {

    fun setUserAddress(addressDataModel: UserAddressDataModel) = addressRepository.setAddress(addressDataModel)



    val _addressDataItem = MutableStateFlow<AddressResultState>(AddressResultState())
    val addressData: StateFlow<AddressResultState> get() = _addressDataItem


    fun getAddress(){
        viewModelScope.launch {

            addressRepository.getAddress().collect{
                when(it){
                    is ResultState.Loading->{
                        _addressDataItem.value = AddressResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _addressDataItem.value = AddressResultState(addressList = it.data)
                    }
                    is ResultState.Error->{
                        _addressDataItem.value = AddressResultState(error = it.message)
                    }
                }
            }
        }
    }
    init {
        getAddress()
    }

    fun deleteAddress(addressId:String) = addressRepository.deleteAddress(addressId)

}

data class AddressResultState(
    var addressList: List<UserAddressDataModel> = emptyList(),
    val isLoading:Boolean = false,
    val error:String = ""
)