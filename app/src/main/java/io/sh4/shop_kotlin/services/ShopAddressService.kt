package io.sh4.shop_kotlin.services

import android.util.Log
import io.sh4.shop_kotlin.models.Product
import io.sh4.shop_kotlin.models.ShopAddress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ShopAddressService {
    fun getShopAddresses() : List<ShopAddress>? {
        val call : Call<List<ShopAddress>> = RetrofitClient.shopAddressApiService.getShopAddresses()
        var shopAddresses : List<ShopAddress>? = null
        Log.d("shopAddresses before", "true")
        call.enqueue(object : Callback<List<ShopAddress>> {
            override fun onResponse(call : Call<List<ShopAddress>>?, response: Response<List<ShopAddress>>?) {
                shopAddresses = response!!.body()
                Log.d("shopAddresses after", shopAddresses.toString())
            }
            override fun onFailure(call : Call<List<ShopAddress>>?, t: Throwable) {
                Log.d("products after", call.toString())
                Log.d("products after", t.toString())
            }
        })
        return shopAddresses
    }

}