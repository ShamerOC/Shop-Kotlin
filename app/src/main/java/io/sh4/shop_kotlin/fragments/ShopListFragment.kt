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