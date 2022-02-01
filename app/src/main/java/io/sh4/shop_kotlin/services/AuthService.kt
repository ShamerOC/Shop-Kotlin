package io.sh4.shop_kotlin.services

import android.util.Log
import io.sh4.shop_kotlin.models.User
import io.sh4.shop_kotlin.models.UserRealm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    lateinit var user : UserRealm

    fun register(email: String, password: String): Boolean {
        createUser(User(name = email, password = password, id = null))
        return true
    }

    private fun createUser(user : User) : User {
        val call : Call<User> = RetrofitClient.userApiService.createUser(user)
        var userResponse : User? = null
        Log.d("User before", "true")
        call.enqueue(object : Callback<User> {
            override fun onResponse(call : Call<User>?, response: Response<User>?) {
                userResponse = response!!.body()
                Log.d("User after", userResponse.toString())
            }
            override fun onFailure(call : Call<User>?, t: Throwable) {
                Log.d("User fail", call.toString())
            }
        })
        return user
    }

}