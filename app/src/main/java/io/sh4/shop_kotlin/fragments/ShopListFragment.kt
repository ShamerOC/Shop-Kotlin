package io.sh4.shop_kotlin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.ShopActivity
import io.sh4.shop_kotlin.adapters.RecyclerViewAdapter
import io.sh4.shop_kotlin.models.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopListFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_shop_list, container, false)
        val context : Context = (activity as ShopActivity)

        var productList : MutableList<Product> = ArrayList()
        for (i in 0..110) {
            productList.add(Product(Integer.toUnsignedLong(i), "test $i", Math.random().toInt()))
        }

        setAllProducts()

        initRecyclerView(v, context, productList)
        return v
    }

//    private fun setAllProducts() : List<Product>? {
//        val call : Call<List<Product>> = productApiService.getProducts()
//        var products : List<Product>? = null
//        Log.d("products before", "true")
//        call.enqueue(object : Callback<List<Product>> {
//            override fun onResponse(call : Call<List<Product>>, response: Response<List<Product>>) {
//                Log.d("products after", products.toString())
//                products = response.body()
//            }
//            override fun onFailure(call : Call<List<Product>>, t: Throwable) {
//            }
//        })
//        return products
//    }

    private fun initRecyclerView(v : View, context : Context, productList : List<Product>) {
        val recyclerView : RecyclerView = v.findViewById(R.id.recycleViewShop)
        val recyclerViewAdapter : RecyclerViewAdapter = RecyclerViewAdapter(context, productList)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShopListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShopListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}