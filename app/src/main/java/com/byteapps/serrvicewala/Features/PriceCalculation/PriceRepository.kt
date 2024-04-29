package com.byteapps.serrvicewala.Features.PriceCalculation

import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface PriceRepository {

    fun setPriceWithCoupon(priceDetails: PriceDetails):Flow<ResultState<PriceDetails>>
}