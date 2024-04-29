package com.byteapps.serrvicewala.Features.PriceCalculation

import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon

data class PriceDetails(
    val price : Int = 0,
    val tax:Int = 0,
    val quantity : Int = 0,
    val total: Int = 0,
    val coupon: Coupon? = null
)






