package io.sh4.shop_kotlin.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Address : RealmObject() {
    @PrimaryKey
    var id : String = "0"
    var user : UserRealm? = null
    var address : String = ""
}