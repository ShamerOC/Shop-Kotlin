package io.sh4.shop_kotlin.services

import android.util.Log
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import io.sh4.shop_kotlin.models.*
import java.util.*

object CartService {
    private val realm = Realm.getDefaultInstance()

    fun add(user: UserRealm, product: ProductRealm) {
        realm.executeTransaction {
            val cart = realm.createObject<CartRealm>(UUID.randomUUID().toString())
            cart.product = product
            cart.user = user
            cart.isActive = true
            Log.d("cart", "added $cart to cart")
        }
//        realm.close()
    }

    fun getAll() : List<CartRealm> {
        val query = realm.where<CartRealm>()
        return query.findAll()
    }

    fun drop(cart : CartRealm) {
        realm.executeTransaction {
            cart.deleteFromRealm()
        }
    }
}