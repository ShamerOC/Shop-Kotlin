package io.sh4.shop_kotlin

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.sh4.shop_kotlin.services.ProductService

class Shop : Application() {
    override fun onCreate() {

        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("Shop.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
        Log.d("Shop.kt", "launching shop.kt")
        configureRetrofit()
        ProductService.upsertDb()
    }

    private fun configureRetrofit() {

    }
}