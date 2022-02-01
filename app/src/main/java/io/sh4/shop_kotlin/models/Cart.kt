package io.sh4.shop_kotlin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

data class Cart(val user : User, val product : Product, val isActive : Boolean) {
//    fun toRealmObject() : CartRealm {
//        return CartRealm(this)
//    }
}

open class CartRealm() : RealmObject(), Serializable {
    @PrimaryKey
    var id : String = "0"
    var user: UserRealm? = null
    var product : ProductRealm? = null
    var isActive : Boolean = false

//    constructor(cart : Cart) : this() {
//        this.user = cart.user.toRealmObject()
//        this.product = cart.product.toRealmObject()
//        this.isActive = cart.isActive
//    }

//    fun toCart() : Cart {
//        return user?.let { product?.let { it1 -> Cart(it.toUser(), it1.toProduct(), isActive) } }!!
//    }
}