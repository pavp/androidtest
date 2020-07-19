package com.example.testandroid.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRequest {
    private const val BASE_URL = "http://www.mocky.io/"
    private val client = OkHttpClient.Builder().build()
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: ApiRequest = getRetrofit().create(ApiRequest::class.java)
}