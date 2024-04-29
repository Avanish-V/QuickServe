package com.byteapps.serrvicewala.Features.UserProfile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteapps.serrvicewala.Utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userProfileRepository: UserProfileRepository): ViewModel() {


    fun setProfile(userProfileDataModel: UserProfileDataModel) = userProfileRepository.setUserProfile(userProfileDataModel)


    private val _userProfileState : MutableState<UserProfileResultState> = mutableStateOf(UserProfileResultState())
    val userProfileState : State<UserProfileResultState> = _userProfileState


    fun getUserProfile(){
        viewModelScope.launch {
            userProfileRepository.getUserProfile().collect{
                when(it){
                    is ResultState.Loading->{
                        _userProfileState.value = UserProfileResultState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _userProfileState.value = UserProfileResultState(profileData = it.data)
                    }
                    is ResultState.Error->{
                        _userProfileState.value = UserProfileResultState(error = it.message)
                    }
                }
            }

        }
    }


}

data class UserProfileResultState(

    val profileData: UserProfileDataModel? = null,
    val isLoading:Boolean = false,
    val error:String = ""

)