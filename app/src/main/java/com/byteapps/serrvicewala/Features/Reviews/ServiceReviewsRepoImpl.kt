package com.byteapps.serrvicewala.Features.Reviews

import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.Features.Reviews.data.GetServiceReviewsDataModel
import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ServiceReviewsRepoImpl @Inject constructor(private val db:FirebaseFirestore, private val auth:FirebaseAuth) :
    ServiceReviewsRepository {

    override fun getServiceReviews(serviceId: String): Flow<ResultState<List<GetServiceReviewsDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Reviews").whereEqualTo("serviceId",serviceId)
            .get()
            .addOnSuccessListener { querySnapshot ->

                val reviewsList = mutableListOf<GetServiceReviewsDataModel>()

                querySnapshot.map {
                    val data = it.toObject(GetServiceReviewsDataModel::class.java)
                    reviewsList.add(data)
                }

                trySend(ResultState.Success(reviewsList))

            }
            .addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))
            }

        awaitClose {
            close()
        }

    }


}