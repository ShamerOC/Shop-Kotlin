package io.sh4.shop_kotlin.api

import io.sh4.shop_kotlin.models.Product
import io.sh4.shop_kotlin.models.ShopAddress
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopAddressApiService {
    @GET("/shopadresses")
    fun getShopAddresses() : Call<List<ShopAddress>>
}