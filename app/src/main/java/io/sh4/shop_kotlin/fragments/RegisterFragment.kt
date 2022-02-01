package io.sh4.shop_kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import io.sh4.shop_kotlin.MainActivity
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.User
import io.sh4.shop_kotlin.services.AuthService
import io.sh4.shop_kotlin.services.RetrofitClient
import io.sh4.shop_kotlin.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        val context = (activity as MainActivity)

        val loginTextView : TextView = v.findViewById(R.id.loginTextView)
        val btnRegister : Button = v.findViewById(R.id.registerButton)

        context.setTitle("Register")

        btnRegister.setOnClickListener {
            register(v)
        }

        loginTextView.setOnClickListener {
            context.replaceFragment(LoginFragment(), false)
        }
        return v
    }

    fun register(view : View) {
        val email = getView()?.findViewById<TextView>(R.id.editTextEmail)?.text.toString()
        val password = getView()?.findViewById<TextView>(R.id.editTextPassword)?.text.toString()
        val passwordRepeat = getView()?.findViewById<TextView>(R.id.editTextPasswordRepeat)?.text.toString()
        if (password == passwordRepeat) {
            val user = User(name = email, password = password, id = null)
            val call : Call<User> = RetrofitClient.userApiService.createUser(user)
            var userResponse : User? = null
            Log.d("User before", "true")
            call.enqueue(object : Callback<User> {
                override fun onResponse(call : Call<User>?, response: Response<User>?) {
                    userResponse = response!!.body()
                    Log.d("User after", userResponse.toString())
                    val userRealm = UserService.getOrAddToRealm(userResponse!!)
                    AuthService.user = userRealm
                    Log.d("User after", userResponse.toString())
                    (activity as MainActivity).changeActivityToShop(view)
                }
                override fun onFailure(call : Call<User>?, t: Throwable) {
                    Log.d("User fail", call.toString())
                }
            })

        } else {
            val a = password == passwordRepeat
            val b = AuthService.register(email, password)
            Log.d("registerFragment", "something went wrong, " + a.toString() + " " + b.toString())
        }
    }
}