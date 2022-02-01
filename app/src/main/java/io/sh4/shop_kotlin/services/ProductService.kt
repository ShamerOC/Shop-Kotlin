package io.sh4.shop_kotlin.services

import android.util.Log
import io.realm.Realm
import io.realm.kotlin.where
import io.sh4.shop_kotlin.models.Product
import io.sh4.shop_kotlin.models.ProductRealm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductService {

    private val realm = Realm.getDefaultInstance()

    fun upsertDb() {
        val call : Call<List<Product>> = RetrofitClient.productApiService.getProducts()
        var products : List<Product>? = null
        Log.d("products before", "true")
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call : Call<List<Product>>?, response: Response<List<Product>>?) {
                products = response!!.body()
                Log.d("products after", products.toString())
                products?.forEach { p -> add(p) }
            }
            override fun onFailure(call : Call<List<Product>>?, t: Throwable) {
                Log.d("products after", t.toString())
            }
        })
    }

    fun add(product : Product) {
        realm.executeTransaction { realmTransaction ->
            val productRealm = ProductRealm(product)
            realmTransaction.insertOrUpdate(productRealm)
        }
    }

    fun getAll() : List<ProductRealm> {
        val query = realm.where<ProductRealm>()
        val resProductRealm : List<ProductRealm> = query.findAll()
        return resProductRealm
    }

}