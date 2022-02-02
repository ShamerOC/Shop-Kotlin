package io.sh4.shop_kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    private lateinit var auth: FirebaseAuth

    private lateinit var view1 : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val context = (activity as MainActivity)
        Log.d("LoginFragment","starting login fragment")
        view1 = v
        val btnLogin : Button = v.findViewById(R.id.loginButton)
        val registerTextView : TextView = v.findViewById(R.id.registerTextView)
        val shopLocalization : TextView = v.findViewById(R.id.shopLocalizationTextView)
        val googleLoginBtn : com.google.android.gms.common.SignInButton = v.findViewById(R.id.signInBtn)

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
            .requestIdToken("19217725685-kh1h84tv68hmnhl81rd8ng753imsdgua.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        auth = Firebase.auth

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("LoginFragment", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginFragment", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener((activity as MainActivity)) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginFragment", "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d("LoginFragment", user.toString())
                    Log.d("LoginFragment", user!!.email.toString())
                    val email = user.email.toString()
                    loginOrRegister(view1, email, email)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginFragment", "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
            }
    }

    fun loginOrRegister(view : View, email : String, password : String) {
        val userOriginal = User(name = email, password = password, id = null)
        val call : Call<User> = RetrofitClient.authApiService.login(userOriginal)
        var userResponse : User? = null
        call.enqueue(object : Callback<User> {
            override fun onResponse(call : Call<User>?, response: Response<User>?) {
                userResponse = response!!.body()
                Log.d("User after", userResponse.toString())
                if (userResponse != null) {
                    val userRealm = UserService.getOrAddToRealm(userResponse!!)
                    AuthService.user = userRealm
                    (activity as MainActivity).changeActivityToShop(view)
                } else {
                    registerUser(view, userOriginal)
                }
            }
            override fun onFailure(call : Call<User>?, t: Throwable) {
                registerUser(view, userOriginal)
                Log.d("User fail", "in login")
            }
        })
    }

    private fun registerUser(view : View, userOriginal : User) {
        val callRegister : Call<User> = RetrofitClient.userApiService.createUser(userOriginal)
        var userResponse : User? = null
        Log.d("User before", "true")
        callRegister.enqueue(object : Callback<User> {
            override fun onResponse(call : Call<User>?, response: Response<User>?) {
                userResponse = response!!.body()
                Log.d("User after", userResponse.toString())
                val userRealm = UserService.getOrAddToRealm(userResponse!!)
                AuthService.user = userRealm
                Log.d("User after", userResponse.toString())
                (activity as MainActivity).changeActivityToShop(view)
            }
            override fun onFailure(call : Call<User>?, t: Throwable) {
                Log.d("User fail", "in register")
            }
        })
    }


    fun login(view : View) {
        val email = getView()?.findViewById<TextView>(R.id.editTextEmail)?.text.toString()
        val password = getView()?.findViewById<TextView>(R.id.editTextPassword)?.text.toString()
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