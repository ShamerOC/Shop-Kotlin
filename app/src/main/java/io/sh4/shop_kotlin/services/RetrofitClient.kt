package io.sh4.shop_kotlin.services

import io.sh4.shop_kotlin.api.AuthApiService
import io.sh4.shop_kotlin.api.ProductApiService
import io.sh4.shop_kotlin.api.ShopAddressApiService
import io.sh4.shop_kotlin.api.UserApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val baseUrl : String = "http://7a1d-159-205-23-198.ngrok.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val productApiService = retrofit.create(ProductApiService::class.java)
    val shopAddressApiService = retrofit.create(ShopAddressApiService::class.java)
    val authApiService = retrofit.create(AuthApiService::class.java)
    val userApiService = retrofit.create(UserApiService::class.java)
}