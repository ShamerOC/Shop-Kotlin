package io.sh4.shop_kotlin.api

import io.sh4.shop_kotlin.models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("/products")
    fun getProducts() : Call<List<Product>>

    @GET("/products/{id}")
    fun getProduct(@Path("id") id : Long) : Call<Product>
}