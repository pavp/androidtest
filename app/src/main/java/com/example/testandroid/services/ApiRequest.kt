package com.example.testandroid.services

import com.example.testandroid.model.Asset
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("v2/5e669952310000d2fc23a20e")
    suspend fun getAssets(): List<Asset>
}