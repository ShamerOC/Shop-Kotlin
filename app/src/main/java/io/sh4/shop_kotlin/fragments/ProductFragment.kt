package io.sh4.shop_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val PRODUCT = "product"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment() {
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.get(PRODUCT) as Product?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_product, container, false)
        if (product == null) return v
        var textView : TextView = v.findViewById(R.id.productNameTextView)
        textView.setText(product!!.name)
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PRODUCT, product)
                }
            }
    }
}