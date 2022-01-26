package io.sh4.shop_kotlin.services

import io.sh4.shop_kotlin.api.ProductApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val baseUrl : String = "http://localhost:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val productApiService = retrofit.create(ProductApiService::class.java)
}