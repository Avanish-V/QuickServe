package com.byteapps.serrvicewala.Features.Orders

import android.util.Log
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.ProfessionalDetails
import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.internal.wait
import javax.inject.Inject

class OrdersRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    OrdersRepository {

    override fun setOrder(ordersDataModel: OrdersDataModel): Flow<ResultState<OrdersDataModel>> =
        callbackFlow {

            trySend(ResultState.Loading)

            ordersDataModel.orderId?.let {

                db.collection("Orders").document(it)
                    .set(ordersDataModel)
                    .addOnSuccessListener {

                        trySend(ResultState.Success(ordersDataModel))
                    }
                    .addOnFailureListener {

                        trySend(ResultState.Error(it.message.toString()))
                    }
            }

            awaitClose {
                close()
            }

        }

    override fun getOrder(): Flow<ResultState<List<OrdersDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Orders")
            .whereEqualTo("userUUID", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->


                val dataList = mutableListOf<OrdersDataModel>()

                val tasksCount = querySnapshot.size()

                querySnapshot.forEach { documentSnapshot ->

                    val data = documentSnapshot.toObject(OrdersDataModel::class.java)

                    // Fetch reviews for each order's professional
                    db.collection("Reviews")
                        .whereEqualTo("professionalId", data.professionalID)
                        .get()
                        .addOnSuccessListener { reviewsQuerySnapshot ->

                            if (reviewsQuerySnapshot.isEmpty) {
                                // If there are no reviews, add the order data directly
                                dataList.add(data)
                            } else {
                                val ratingData = mutableListOf<Int>()

                                // Extract ratings from reviews
                                reviewsQuerySnapshot.forEach { reviewSnapshot ->
                                    val rating = reviewSnapshot.data["rating"].toString().toInt()
                                    ratingData.add(rating)
                                }

                                // Calculate total rating and count of reviews
                                val totalRating = ratingData.sum()
                                val reviewCount = ratingData.size

                                // Modify order data to include professional details with rating info
                                val modifiedData = data.copy(
                                    professionalDetail = ProfessionalDetails(
                                        professionalName = data.professionalDetail?.professionalName
                                            ?: "",
                                        professionalImage = data.professionalDetail?.professionalImage
                                            ?: "",
                                        rating = totalRating,
                                        count = reviewCount
                                    )
                                )

                                dataList.add(modifiedData)
                            }

                            // If all tasks are completed, send success result
                            if (dataList.size == tasksCount) {
                                trySend(ResultState.Success(dataList.toList()))
                                close()
                            }


                        }
                }
            }
            .addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))
            }




        awaitClose {
            close()
        }
    }

    override fun cancelOrder(orderId: String, status: Status): Flow<ResultState<Status>> =
        callbackFlow {

            trySend(ResultState.Loading)

            db.collection("Orders").document(orderId)
                .update("status", "CANCELED")
                .addOnSuccessListener { querySnapshot ->

                    trySend(ResultState.Success(status))
                }
                .addOnFailureListener {

                    trySend(ResultState.Error(it.message.toString()))
                }

            awaitClose {
                close()
            }

        }

    override fun setReview(reviewDataModel: ReviewDataModel): Flow<ResultState<ReviewDataModel>> =
        callbackFlow {

            trySend(ResultState.Loading)

            db.collection("Reviews").document(reviewDataModel.orderId)
                .set(reviewDataModel)
                .addOnSuccessListener {
                    trySend(ResultState.Success(reviewDataModel))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }

            awaitClose {
                close()
            }

        }

    override fun getReview(orderId: String): Flow<ResultState<Map<String, String>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Reviews").document(orderId)
            .get()
            .addOnSuccessListener {


            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }

        awaitClose {
            close()
        }


    }

}