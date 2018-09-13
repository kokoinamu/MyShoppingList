package com.example.shota_inamura.myshoppinglist

import android.app.Application
import io.realm.Realm

class MyShoppingListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}