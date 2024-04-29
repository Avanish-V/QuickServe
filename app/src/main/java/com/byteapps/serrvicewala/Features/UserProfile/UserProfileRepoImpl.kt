package com.byteapps.serrvicewala.Features.UserProfile

import com.byteapps.serrvicewala.Utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserProfileRepoImpl @Inject constructor(private val db:FirebaseFirestore,private val auth:FirebaseAuth): UserProfileRepository{

    override fun setUserProfile(userProfileDataModel: UserProfileDataModel): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("UsersProfile").document(auth.currentUser!!.uid)
            .set(userProfileDataModel)
            .addOnSuccessListener {

                trySend(ResultState.Success("Submitted!"))

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))


        }
        awaitClose {
            close()
        }

    }

    override fun getUserProfile(): Flow<ResultState<UserProfileDataModel>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("UsersProfile").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {

                val profileData = it.toObject(UserProfileDataModel::class.java)

                if (profileData != null){
                    trySend(ResultState.Success(profileData))
                }

            }.addOnFailureListener {

                trySend(ResultState.Error(it.message.toString()))


            }
        awaitClose {
            close()
        }
    }


}