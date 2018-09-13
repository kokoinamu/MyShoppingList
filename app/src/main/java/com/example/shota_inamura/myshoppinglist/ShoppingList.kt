package com.example.shota_inamura.myshoppinglist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ShoppingList : RealmObject() {
    @PrimaryKey var id: Long = 0
    var name: String = ""
    var number: Int = 0
}