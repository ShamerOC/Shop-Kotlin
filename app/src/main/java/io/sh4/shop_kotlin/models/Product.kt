package io.sh4.shop_kotlin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

data class Product (
    var id: Long = 0,
    var name : String? = null,
    var qty : Int = 0,
    var price : Double = 0.0,
    var bio : String = ""
) : Serializable

open class ProductRealm : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name : String? = null
    var qty : Int = 0
    var price : Double = 0.0
    var bio : String = ""
}