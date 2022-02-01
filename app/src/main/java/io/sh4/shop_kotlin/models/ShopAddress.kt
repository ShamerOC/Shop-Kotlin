package io.sh4.shop_kotlin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class ShopAddress (
    val id : String = "0",
    val lat : Double = 0.0,
    val lon : Double = 0.0
)