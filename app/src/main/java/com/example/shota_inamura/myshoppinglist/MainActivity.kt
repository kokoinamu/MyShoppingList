package com.example.shota_inamura.myshoppinglist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        realm = Realm.getDefaultInstance()
        val shoppingLists = realm.where<ShoppingList>().findAll()
        listView.adapter = ShoppingListAdapter(shoppingLists)

        listView.setOnItemClickListener { parent, view, position, id ->
            val shoppingList = parent.getItemAtPosition(position) as ShoppingList
            startActivity<ShoppingListEditActivity>("shopping_list_id" to shoppingList.id)
        }

        fab.setOnClickListener { view ->
            startActivity<ShoppingListEditActivity>()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
