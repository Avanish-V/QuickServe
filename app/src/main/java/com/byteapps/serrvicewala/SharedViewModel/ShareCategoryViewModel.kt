package com.byteapps.serrvicewala.SharedViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ShareCategoryViewModel : ViewModel() {

    private val _categoryId:MutableState<String> = mutableStateOf("")
    val categoryId:State<String> = _categoryId


    fun setCategoryId(category:String){
        _categoryId.value = category
    }
}