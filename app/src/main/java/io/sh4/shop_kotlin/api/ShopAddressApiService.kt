package io.sh4.shop_kotlin.api

import io.sh4.shop_kotlin.models.ShopAddress
import retrofit2.Call
import retrofit2.http.GET

interface ShopAddressApiService {
    @GET("/shopadresses")
    fun getShopAddresses() : Call<List<ShopAddress>>
}