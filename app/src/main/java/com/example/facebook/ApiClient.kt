package com.example.facebook

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val okHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)   // thêm client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
