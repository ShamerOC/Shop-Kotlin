package io.sh4.shop_kotlin.services

import android.util.Log

object AuthService {
    fun login(email: String, password: String): Boolean {
        Log.d("AuthService","successfully logged")
        return true
    }

    fun register(email: String, password: String): Boolean {
        return true
    }

}