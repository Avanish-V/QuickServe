package com.byteapps.serrvicewala.Features.Crousel

import androidx.compose.runtime.mutableStateOf
import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CarouselRepositoryImpl @Inject constructor(private val db:FirebaseFirestore): CarouselRepository {

    override fun getCarousel(): Flow<ResultState<List<CarouselDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Carousel")
            .get()
            .addOnSuccessListener { querySnapshot ->

                val carouselData = mutableListOf<CarouselDataModel>()

                querySnapshot.map {

                    val data = it.toObject(CarouselDataModel::class.java)
                    carouselData.add(data)
                }

                trySend(ResultState.Success(carouselData))

            }
            .addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

       awaitClose {
           close()
       }
    }
}