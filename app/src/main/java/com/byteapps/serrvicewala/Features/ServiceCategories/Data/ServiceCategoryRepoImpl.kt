package com.byteapps.serrvicewala.Features.ServiceCategories.Data

import android.util.Log
import com.byteapps.serrvicewala.Features.ServiceCategories.Domain.Repository.ServiceCategoryRepository
import com.byteapps.serrvicewala.Utils.ResultState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.tasks.Tasks.whenAllSuccess
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateField
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class ServiceCategoryRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ServiceCategoryRepository {

    override fun getServiceCategories(): Flow<ResultState<List<ServiceCategoryDataModel>>> =
        callbackFlow {

            trySend(ResultState.Loading)

            db.collection("ServiceCategories").get()


                .addOnSuccessListener { querySnapshot ->

                    val categoryList = mutableListOf<ServiceCategoryDataModel>()

                    querySnapshot?.map {

                        val categoryData = it.toObject(ServiceCategoryDataModel::class.java)
                        categoryList.add(categoryData)

                    }

                    trySend(ResultState.Success(categoryList))

                }
                .addOnFailureListener {

                    trySend(ResultState.Error(it.message.toString()))

                }

            awaitClose {
                close()
            }

        }

    override fun getServices(serviceCategoryId: String): Flow<ResultState<List<ServiceProductDataModel>>> =
        callbackFlow {

            trySend(ResultState.Loading)

            val couponsPath = db.collection("ServiceProducts").document("Appliances").collection(serviceCategoryId)
            val descriptionPath = db.collection("ServiceProducts").document("Appliances").collection(serviceCategoryId).document().collection("Coupons")
            val serviceDataPath = db.collection("ServiceProducts").document("Appliances").collection(serviceCategoryId).document().collection("Description")

            val couponsTask = couponsPath.get()
            val descriptionTask = descriptionPath.get()
            val serviceDataTask = serviceDataPath.get()


            descriptionPath.get().addOnSuccessListener { it ->
                it.map {

                }
            }
//            Tasks.whenAllComplete(couponsTask, descriptionTask, serviceDataTask)
//                .addOnSuccessListener { result ->
//                    result.forEachIndexed { index, taskResult ->
//                        if (taskResult.isSuccessful) {
//                            val querySnapshot = taskResult.result as QuerySnapshot
//                            when (index) {
//                                0 -> {
//                                    // Handle couponsPath query snapshot
//                                    querySnapshot.documents.forEach { document ->
//                                        Log.d("Coupons Document", document.id + " => " + document.data)
//                                    }
//                                }
//                                1 -> {
//                                    // Handle descriptionPath query snapshot
//                                    querySnapshot.documents.forEach { document ->
//                                        Log.d("Description Document", document.id + " => " + document.data)
//                                    }
//                                }
//                                2 -> {
//                                    // Handle serviceDataPath query snapshot
//                                    querySnapshot.documents.forEach { document ->
//                                        Log.d("Service Data Document", document.id + " => " + document.data)
//                                    }
//                                }
//                            }
//                        } else {
//                            // Handle failure of individual task
//                            Log.e("Firestore Task", "Task failed: ${taskResult.exception}")
//                        }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    // Handle overall failure of all tasks
//                    Log.e("Firestore Task", "All tasks failed: $exception")
//                }

            // Combine all the queries


            db.collection("ServiceProducts")
                .whereEqualTo("serviceTAG",serviceCategoryId)
                .get()
                .addOnSuccessListener { querySnapshot ->

                    val serviceProductList = mutableListOf<ServiceProductDataModel>()

                    querySnapshot.map {

                        val serviceProduct = it.toObject(ServiceProductDataModel::class.java)

                        serviceProductList.add(serviceProduct)

                    }

                    trySend(ResultState.Success(serviceProductList))

                }.addOnFailureListener {

                    trySend(ResultState.Error(it.message.toString()))
                }

            awaitClose {
                close()
            }
        }
}