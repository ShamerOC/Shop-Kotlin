package io.sh4.shop_kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.stripe.android.PaymentConfiguration
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.CartRealm
import io.sh4.shop_kotlin.services.AddressService
import okhttp3.OkHttpClient
import java.io.Serializable
import java.lang.StringBuilder

private const val CART_LIST = "CART_LIST"

class PurchaseFragment : Fragment() {
    private var cartList : ArrayList<CartRealm>? = null
    private val httpClient = OkHttpClient()
    private lateinit var publishableKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cartList = it.getSerializable(CART_LIST) as ArrayList<CartRealm>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_purchase, container, false)
        val purchaseButton : Button = v.findViewById(R.id.purchaseButton)
        setAddress(v)
        evaluateAndSetCartListTextView(v)

        purchaseButton.setOnClickListener {
            purchaseButtonPressed(v)
        }
        Log.d("PurchaseFragment", cartList.toString())
        return v
    }

    private fun purchaseButtonPressed(v : View) {
        val savedAddressTextView : TextView = v.findViewById(R.id.savedAddressTextView)
        val newAddressTextView : TextView = v.findViewById(R.id.newAddressTextView)
        Log.d("PurchaseFragment", "purchaseButtonPressed")
        var address : CharSequence?
        if (newAddressTextView.text.isEmpty()) {
            address = savedAddressTextView.text.toString()
        } else {
            address = newAddressTextView.text.toString()
            AddressService.add(address.toString())
        }
        Log.d("PurchaseFragment", "address: $address")
    }

    private fun setAddress(v : View) {
        val address = AddressService.get()
        Log.d("address", address?.toString() ?: "No address found")
        val savedAddressTextView : TextView = v.findViewById(R.id.savedAddressTextView)
        val addressText = address?.address ?: "No address saved"
        savedAddressTextView.text = addressText
    }

    private fun evaluateAndSetCartListTextView(v : View) {
        val cartListTextView : TextView = v.findViewById(R.id.cartListTextView)
        val text = StringBuilder()
        cartList!!.forEach { x ->
            text.append(x.product!!.name)
                .append(": ")
                .append(x.product!!.price)
                .append("\n")
        }
        val sum = cartList!!.sumOf { x -> x.product!!.price }
        text.append("Total: ")
            .append(sum.toString())
        cartListTextView.text = text
    }

    private fun fetchPublishableKey() {
        this.publishableKey = ""
        PaymentConfiguration.init(context!!, publishableKey)
    }

    companion object {
        @JvmStatic fun newInstance(cartList: ArrayList<CartRealm>) =
                PurchaseFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(CART_LIST, cartList as Serializable)
                    }
                }
    }
}