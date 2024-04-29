package com.byteapps.serrvicewala.Features.Reviews

import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Reviews.data.GetServiceReviewsDataModel
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ServiceReviewsRepository  {



    fun getServiceReviews(serviceId: String):Flow<ResultState<List<GetServiceReviewsDataModel>>>



}