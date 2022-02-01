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
import io.sh4.shop_kotlin.models.Cart
import io.sh4.shop_kotlin.models.CartRealm
import io.sh4.shop_kotlin.models.Product
import io.sh4.shop_kotlin.models.User
import io.sh4.shop_kotlin.services.CartService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}