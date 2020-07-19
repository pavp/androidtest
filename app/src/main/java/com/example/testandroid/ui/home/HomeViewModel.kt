package com.example.testandroid.ui.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testandroid.model.User
import com.example.testandroid.services.AssetRepository
import com.example.testandroid.services.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel() : ViewModel() {
    private val assetRepository: AssetRepository = AssetRepository()
    val user: MutableLiveData<User> = MutableLiveData<User>()

    init {
        initUser()
    }

    fun getAssets() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = assetRepository.getAssets()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun initUser() {
        var user1: User = User()
        user1.name = "David Lopez"
        user1.username = "@davidQQ"
        user1.biography = "Soy un desarrollador!"
        user1.profilePicture = " "

        user.value = user1
    }

    fun setUser(user: User) {
        this.user.value = user
    }
}