package com.example.testandroid.services

class AssetRepository {
    var client = RetrofitRequest.apiService
    suspend fun getAssets() = client.getAssets()
}