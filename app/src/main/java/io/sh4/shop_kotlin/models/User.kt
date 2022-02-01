package io.sh4.shop_kotlin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class User(
    var id : Int?,
    var name : String,
    var password : String
) {
    fun toRealm() : UserRealm {
        val user = UserRealm()
        user.name = name
        user.id = id!!
        return user
    }
}

open class UserRealm() : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var name : String? = null

//    fun toUser() : User {
//        return User(this.id, this.name!!)
//    }
}