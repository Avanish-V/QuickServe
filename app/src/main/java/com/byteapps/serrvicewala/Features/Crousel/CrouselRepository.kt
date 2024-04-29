package com.byteapps.serrvicewala.Features.Crousel

import com.byteapps.serrvicewala.Utils.ResultState
import java.util.concurrent.Flow

interface CarouselRepository {

    fun getCarousel():kotlinx.coroutines.flow.Flow<ResultState<List<CarouselDataModel>>>
}