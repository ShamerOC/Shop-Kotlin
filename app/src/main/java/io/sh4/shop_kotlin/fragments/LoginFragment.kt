package io.sh4.shop_kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.sh4.shop_kotlin.MainActivity
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.User
import io.sh4.shop_kotlin.services.AuthService
import io.sh4.shop_kotlin.services.RetrofitClient
import io.sh4.shop_kotlin.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val context = (activity as MainActivity)
        Log.d("LoginFragment","starting login fragment")

        val btnLogin : Button = v.findViewById(R.id.loginButton)
        val registerTextView : TextView = v.findViewById(R.id.registerTextView)
        val shopLocalization : TextView = v.findViewById(R.id.shopLocalizationTextView)
        val googleLoginBtn : Button = v.findViewById(R.id.google_login_btn)

        context.setTitle("Login")

        btnLogin.setOnClickListener {
            login(v)
        }

        registerTextView.setOnClickListener {
            context.replaceFragment(RegisterFragment(), true)
        }

        shopLocalization.setOnClickListener {
            context.replaceFragment(MapsFragment(), true)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("439656339065-hjd0t3eskrl5mgbp9r1e4ro3n96tsvbr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient((activity as MainActivity), gso)

        googleLoginBtn.setOnClickListener {
            signIn()
        }

        return v
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }

    fun login(view : View) {
        var email = getView()?.findViewById<TextView>(R.id.editTextEmail)?.text.toString()
        var password = getView()?.findViewById<TextView>(R.id.editTextPassword)?.text.toString()
        val call : Call<User> = RetrofitClient.authApiService.login(User(name = email, password = password, id = null))
        var userResponse : User? = null
        call.enqueue(object : Callback<User> {
            override fun onResponse(call : Call<User>?, response: Response<User>?) {
                userResponse = response!!.body()
                Log.d("User after", userResponse.toString())
                if (userResponse != null) {
                    val userRealm = UserService.getOrAddToRealm(userResponse!!)
                    AuthService.user = userRealm
                    (activity as MainActivity).changeActivityToShop(view)
                }
            }
            override fun onFailure(call : Call<User>?, t: Throwable) {
                Log.d("User fail", call.toString())
            }
        })
        }
}