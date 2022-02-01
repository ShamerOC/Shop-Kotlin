package io.sh4.shop_kotlin.api

import io.sh4.shop_kotlin.models.Product
import io.sh4.shop_kotlin.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("/login")
    fun login(@Body user : User) : Call<User>
}