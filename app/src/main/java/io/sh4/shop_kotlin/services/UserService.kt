package io.sh4.shop_kotlin.services

import io.realm.Realm
import io.realm.kotlin.where
import io.sh4.shop_kotlin.models.User
import io.sh4.shop_kotlin.models.UserRealm

object UserService {
    private val realm = Realm.getDefaultInstance()

    fun getOrAddToRealm(user : User) : UserRealm {
        realm.executeTransaction { realmTransaction ->
            realmTransaction.insertOrUpdate(user.toRealm())
        }
        val query = realm.where<UserRealm>()
        val users: List<UserRealm> = query.findAll()
        users.forEach { userR ->
            if (userR.name == user.name) return userR
        }
        return user.toRealm()
    }

    fun get() : UserRealm? {
        val query = realm.where<UserRealm>()
        return query.findFirst()
    }
}