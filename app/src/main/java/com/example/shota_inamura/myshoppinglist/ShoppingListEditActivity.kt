package com.example.shota_inamura.myshoppinglist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_shopping_list_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ShoppingListEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_edit)
        realm = Realm.getDefaultInstance()

        val shoppingListId = intent?.getLongExtra("shopping_list_id", -1L)
        if (shoppingListId != -1L) {
            val shoppingList = realm.where<ShoppingList>().equalTo("id", shoppingListId).findFirst()
            nameEditText.setText(shoppingList?.name)
            numberText.text = shoppingList?.number.toString()
            deleteBtn.visibility = View.VISIBLE
        } else {
            deleteBtn.visibility = View.INVISIBLE
        }

        plusBtn.setOnClickListener {
            var number = numberText.text.toString().toInt()
            number++
            numberText.text = number.toString()
        }

        minusBtn.setOnClickListener {
            var number = numberText.text.toString().toInt()
            if (number > 0) {
                number--
                numberText.text = number.toString()
            }
        }

        saveBtn.setOnClickListener {
            val name = nameEditText.text.toString()

            if (name.isEmpty()) {
                alert ("商品名が入力されていません") {
                    yesButton {}
                }.show()
            } else {
                when (shoppingListId) {
                    -1L -> {
                        realm.executeTransaction {
                            val maxId = realm.where<ShoppingList>().max("id")
                            val nextId = (maxId?.toLong() ?: 0L) + 1
                            val shoppingList = realm.createObject<ShoppingList>(nextId)
                            shoppingList.name = name
                            shoppingList.number = numberText.text.toString().toInt()
                        }
                        alert ("保存しました") {
                            yesButton { finish() }
                        }.show()
                    }
                    else -> {
                        realm.executeTransaction {
                            val shoppingList = realm.where<ShoppingList>().equalTo("id", shoppingListId).findFirst()
                            shoppingList?.name = name
                            shoppingList?.number = numberText.text.toString().toInt()
                        }
                        alert ("修正しました") {
                            yesButton { finish() }
                        }.show()
                    }
                }
            }
        }

        deleteBtn.setOnClickListener {
            realm.executeTransaction {
                realm.where<ShoppingList>().equalTo("id", shoppingListId)?.findFirst()?.deleteFromRealm()
            }
            alert ("消去しました") {
                yesButton { finish() }
            }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
