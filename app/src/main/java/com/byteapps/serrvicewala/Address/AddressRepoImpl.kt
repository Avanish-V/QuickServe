package com.byteapps.serrvicewala.Address

import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(private val db:FirebaseFirestore,private val auth:FirebaseAuth) : AddressRepository {

    override fun setAddress(addressData: UserAddressDataModel): Flow<ResultState<UserAddressDataModel>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("UsersProfile")
            .document(auth.currentUser!!.uid)
            .collection("Address")
            .document(addressData.addressId)
            .set(addressData)
            .addOnSuccessListener {

                trySend(ResultState.Success(addressData))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

        awaitClose {
            close()
        }
    }

    override fun getAddress(): Flow<ResultState<List<UserAddressDataModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Users")
            .document(auth.currentUser!!.uid)
            .collection("Address")
            .get()
            .addOnSuccessListener { querySnapshot ->

                val addressList = mutableListOf<UserAddressDataModel>()

                querySnapshot.map {
                    val data = it.toObject(UserAddressDataModel::class.java)
                    addressList.add(data)
                }

                trySend(ResultState.Success(addressList))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

        awaitClose {
            close()
        }
    }

    override fun deleteAddress(addressId: String): Flow<ResultState<Boolean>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("Users")
            .document(auth.currentUser!!.uid)
            .collection("Address")
            .document(addressId)
            .delete()
            .addOnSuccessListener {

                trySend(ResultState.Success(true))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))

            }

        awaitClose {
            close()
        }
    }

}