package io.sh4.shop_kotlin.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.ShopActivity
import io.sh4.shop_kotlin.adapters.RecyclerViewAdapter
import io.sh4.shop_kotlin.models.ProductRealm
import io.sh4.shop_kotlin.services.ProductService

class ShopListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_shop_list, container, false)
        val context : Context = (activity as ShopActivity)

        val productList : List<ProductRealm> = ProductService.getAll()

        val cartButton : Button = v.findViewById(R.id.cartButton)

        cartButton.setOnClickListener {
            (it!!.context as ShopActivity).replaceFragment(CartFragment(), true)
            Log.d("ShopList", "Switching cart fragment")
        }

        initRecyclerView(v, context, productList)
        return v
    }

    private fun initRecyclerView(v: View, context: Context, productList: List<ProductRealm>) {
        val recyclerView : RecyclerView = v.findViewById(R.id.recycleViewShop)
        val recyclerViewAdapter : RecyclerViewAdapter = RecyclerViewAdapter(context, productList)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}