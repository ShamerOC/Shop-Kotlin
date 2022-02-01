package io.sh4.shop_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.ProductRealm
import io.sh4.shop_kotlin.services.AuthService
import io.sh4.shop_kotlin.services.CartService

private const val PRODUCT = "product"

class ProductFragment : Fragment() {
    private var product: ProductRealm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.get(PRODUCT) as ProductRealm?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_product, container, false)
        if (product == null) return v
        val productNameTextView : TextView = v.findViewById(R.id.productNameTextView)
        val productDescriptionTextView : TextView = v.findViewById(R.id.productDescriptionTextView)
        val priceTextView : TextView = v.findViewById(R.id.priceTextView)

        productNameTextView.setText(product!!.name)
        productDescriptionTextView.setText(product!!.bio)
        val price : String = "$" + product!!.price.toString()
        priceTextView.setText(price)

        addToCartOnClickListener(v)

        return v
    }

    private fun addToCartOnClickListener(v: View) {
        val button : Button = v.findViewById(R.id.addToCartButton)
        button.setOnClickListener {
            addToCart()
        }
    }

    private fun addToCart() {
        val user = AuthService.user
        CartService.add(user, product!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(product: ProductRealm) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PRODUCT, product)
                }
            }
    }
}