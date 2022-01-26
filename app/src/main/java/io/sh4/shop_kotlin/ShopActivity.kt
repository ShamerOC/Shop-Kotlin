package io.sh4.shop_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

    }

    fun replaceFragment(fragment: Fragment, addToBackStack : Boolean){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}