package io.sh4.shop_kotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sh4.shop_kotlin.R
import io.sh4.shop_kotlin.ShopActivity
import io.sh4.shop_kotlin.fragments.ProductFragment
import io.sh4.shop_kotlin.models.ProductRealm

class RecyclerViewAdapter(private val context: Context, public val productList: List<ProductRealm> = ArrayList()) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.image_name)
//        val circleImageView : CircleImageView = itemView.findViewById(R.id.image)
        val parentLayout : RelativeLayout = itemView.findViewById(R.id.parentLayout)
        var id : Long = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList.get(position)
        holder.textView.setText(currentProduct.name)
        holder.id = currentProduct.id
        Log.d("RecyclerViewAdapter", "loading postition: $position, item count: $itemCount")
        Log.d("", "loading postition: $position")
        holder.parentLayout.setOnClickListener {
            (it!!.context as ShopActivity).replaceFragment(ProductFragment.newInstance(currentProduct), true)
            Log.d("RecyclerViewAdapter","im in onBindViewHolder")
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
