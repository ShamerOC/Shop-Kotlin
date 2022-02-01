package io.sh4.shop_kotlin.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.ShopActivity
import io.sh4.shop_kotlin.adapters.CartRecyclerViewAdapter
import io.sh4.shop_kotlin.models.CartRealm
import io.sh4.shop_kotlin.services.CartService

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_cart, container, false)
        val context : Context = (activity as ShopActivity)

        val cartList = CartService.getAll()
        val totalPrice = cartList.sumOf { p -> p.product!!.price }

        updateTotalPrice(v, totalPrice)
        val recyclerView = initRecyclerView(v, context, cartList)

        val button : Button = v.findViewById(R.id.buyButton)

        button.setOnClickListener {
            val cartList = recyclerView.cartList
            (it!!.context as ShopActivity).replaceFragment(PurchaseFragment.newInstance(ArrayList(cartList)), true)
            Log.d("CartFragment", "Switching to purchase")
        }

        return v
    }

    private fun initRecyclerView(v : View, context : Context, cartList : List<CartRealm>) : CartRecyclerViewAdapter {
        val recyclerView : RecyclerView = v.findViewById(R.id.recycleViewCart)
        val cartRecyclerViewAdapter : CartRecyclerViewAdapter = CartRecyclerViewAdapter(context, cartList, v)
        recyclerView.adapter = cartRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return cartRecyclerViewAdapter
    }

    private fun updateTotalPrice(v : View, totalPrice : Double) {
        val totalPriceTextView: TextView = v.findViewById(R.id.totalPriceTextView)
        totalPriceTextView.text = totalPrice.toString()
    }
}