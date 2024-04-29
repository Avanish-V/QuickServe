package com.byteapps.serrvicewala.Address

import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    fun setAddress(addressData:UserAddressDataModel):Flow<ResultState<UserAddressDataModel>>

    fun getAddress():Flow<ResultState<List<UserAddressDataModel>>>

    fun deleteAddress(addressId:String):Flow<ResultState<Boolean>>

}