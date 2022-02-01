package io.sh4.shop_kotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.models.CartRealm
import io.sh4.shop_kotlin.services.CartService

class CartRecyclerViewAdapter(private val context: Context, val cartList: List<CartRealm> = ArrayList(), private val fragmentView : View) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)
        var id : Long = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_cartitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCart = cartList.get(position)
        holder.nameTextView.setText(currentCart.product!!.name)
        holder.id = currentCart.product!!.id
        Log.d("CartRecyclerViewAdapter", "loading postition: $position, item count: $itemCount")
        holder.removeButton.setOnClickListener {
            cartList.drop(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartList.size);
            Log.d("CartRecyclerViewAdapter", "Removing at: $position, item count: $itemCount")
            CartService.drop(currentCart)
            var totalPrice : Double = cartList.sumOf { x -> x.product!!.price }
            updateTotalPrice(totalPrice)
        }
    }

    private fun updateTotalPrice(totalPrice : Double) {
        val totalPriceTextView: TextView = fragmentView.findViewById(R.id.totalPriceTextView)
        totalPriceTextView.text = totalPrice.toString()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


}