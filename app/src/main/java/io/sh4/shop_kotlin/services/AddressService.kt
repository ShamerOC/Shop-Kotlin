package io.sh4.shop_kotlin.services

import android.util.Log
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import io.sh4.shop_kotlin.models.Address
import io.sh4.shop_kotlin.services.AuthService.user
import java.util.*

object AddressService {
    private val realm = Realm.getDefaultInstance()

    fun add(addressString: String) {
        realm.executeTransaction {
            val address = realm.createObject<Address>(UUID.randomUUID().toString())
            address.user = user
            address.address = addressString
            Log.d("AddressService", "added $address to addresses")
        }
    }

    fun get() : Address? {
        val addressList = realm.where<Address>()
            .findAll()
            .filter { address ->
                address.user!!.id == user.id
            }
        if (addressList.size > 0) {
            return addressList[addressList.size - 1]
        } else {
            return null
        }
    }

}