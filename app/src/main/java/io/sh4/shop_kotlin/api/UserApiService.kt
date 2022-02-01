package io.sh4.shop_kotlin.api

import io.sh4.shop_kotlin.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/users")
    fun createUser(@Body user : User) : Call<User>
}