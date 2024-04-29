package com.byteapps.serrvicewala.Features.Offers

import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface OfferRepository {

    fun getOffers():Flow<ResultState<List<OfferDataModel>>>

    fun getOfferById(tag:String):Flow<ResultState<List<OfferDataModel>>>

}