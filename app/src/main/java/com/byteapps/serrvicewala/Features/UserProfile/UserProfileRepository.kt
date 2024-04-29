package com.byteapps.serrvicewala.Features.UserProfile

import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    fun setUserProfile(userProfileDataModel: UserProfileDataModel) : Flow<ResultState<String>>

    fun getUserProfile() : Flow<ResultState<UserProfileDataModel>>





}