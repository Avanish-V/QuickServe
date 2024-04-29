package com.byteapps.serrvicewala.Features.PriceCalculation

import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PriceRepositoryImpl @Inject constructor() : PriceRepository {


    override fun setPriceWithCoupon(priceDetails: PriceDetails): Flow<ResultState<PriceDetails>> =

        callbackFlow {

            trySend(ResultState.Loading)

            try {


                if (priceDetails.coupon?.couponCode == null) {

                    trySend(
                        ResultState.Success(
                            PriceDetails(
                                price = priceDetails.price,
                                tax = priceDetails.tax,
                                total = priceWithTax(
                                    priceDetails.price,
                                    priceDetails.tax,
                                    priceDetails.quantity
                                ),
                                quantity = priceDetails.quantity,
                                coupon = Coupon(
                                    offerTitle = null,
                                    couponCode = null,
                                    discount = null

                                )

                            )
                        )
                    )

                } else {

                    val priceWithTax = priceWithTax(
                        priceDetails.price,
                        priceDetails.tax,
                        priceDetails.quantity
                    )

                    val offerDiscount = offerDiscount(
                        priceWithTax = priceWithTax(
                            priceDetails.price,
                            priceDetails.tax,
                            priceDetails.quantity
                        ),
                        percentDiscount = priceDetails.coupon?.discount ?: 0
                    )



                    val total = totalPrice(
                        priceWithTax = priceWithTax,
                        offerDiscount = offerDiscount
                    )

                    trySend(
                        ResultState.Success(
                            PriceDetails(
                                price = priceDetails.price,
                                tax = priceDetails.tax,
                                total = total,
                                quantity = priceDetails.quantity,
                                coupon = Coupon(
                                    offerTitle = "",
                                    couponCode = priceDetails.coupon?.couponCode ?: "",
                                    discount = offerDiscount

                                )

                            )
                        )
                    )
                }


            } catch (e: Exception) {

            }
            awaitClose {
                close()
            }


        }


}

private fun priceWithTax(price: Int, tax: Int, quantity: Int): Int {
    return (price * quantity) + (tax * quantity)
}

private fun offerDiscount(priceWithTax: Int, percentDiscount: Int): Int {
    return (priceWithTax * percentDiscount) / 100
}

private fun totalPrice(priceWithTax: Int, offerDiscount: Int): Int {
    return priceWithTax - offerDiscount
}