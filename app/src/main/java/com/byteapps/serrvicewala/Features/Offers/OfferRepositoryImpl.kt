package com.byteapps.serrvicewala.Features.Offers

import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(private val db:FirebaseFirestore) : OfferRepository {

    override fun getOffers(): Flow<ResultState<List<OfferDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Offers").get()
            .addOnSuccessListener { querySnapshot ->

                val dataList = mutableListOf<OfferDataModel>()

                querySnapshot.map {
                    val data = it.toObject(OfferDataModel::class.java)
                    dataList.add(data)
                }

                trySend(ResultState.Success(dataList))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

        awaitClose {
            close()
        }

    }

    override fun getOfferById(tag:String): Flow<ResultState<List<OfferDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Offers")
            .whereEqualTo("offerTAG",tag)
            .get()
            .addOnSuccessListener { querySnapshot ->

                val dataList = mutableListOf<OfferDataModel>()

                querySnapshot.map {
                    val data = it.toObject(OfferDataModel::class.java)
                    dataList.add(data)
                }

                trySend(ResultState.Success(dataList))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

        awaitClose {
            close()
        }

    }


}