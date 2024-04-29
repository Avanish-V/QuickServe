package com.byteapps.serrvicewala.Features.ServiceCategories.Domain.Repository

import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceCategoryDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceProductDataModel
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ServiceCategoryRepository {

    fun getServiceCategories():Flow<ResultState<List<ServiceCategoryDataModel>>>

    fun getServices(serviceCategoryId:String):Flow<ResultState<List<ServiceProductDataModel>>>

}