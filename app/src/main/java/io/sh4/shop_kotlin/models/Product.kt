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
) : Serializable {
    constructor(product : ProductRealm) : this() {
        this.id = product.id
        this.name = product.name
        this.qty = product.qty
        this.price = product.price
        this.bio = product.bio
    }

    fun toRealmObject() : ProductRealm {
        return ProductRealm(this)
    }
}

open class ProductRealm() : RealmObject(), Serializable {
    @PrimaryKey
    var id: Long = 0
    var name : String? = null
    var qty : Int = 0
    var price : Double = 0.0
    var bio : String = ""

    constructor(product : Product) : this() {
        this.id = product.id
        this.name = product.name
        this.qty = product.qty
        this.price = product.price
        this.bio = product.bio
    }

    fun toProduct() : Product {
        return Product(this)
    }
}