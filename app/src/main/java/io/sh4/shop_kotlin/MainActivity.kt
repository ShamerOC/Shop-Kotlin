package io.sh4.shop_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeActivityToShop(view: View) {
        val intent = Intent(this, ShopActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun setTitle(titleText: String) {
        var title : TextView = findViewById(R.id.titleTextView)
        title.setText(titleText)
    }

    fun replaceFragment(fragment: Fragment, addToBackStack : Boolean){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView2,fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}