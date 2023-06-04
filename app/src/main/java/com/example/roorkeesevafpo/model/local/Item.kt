package com.example.roorkeesevafpo.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class Item(
    @ColumnInfo(name = "item_name")
    var name: String,

    @ColumnInfo(name = "amount")
    var amount: Int? = 0,

    @ColumnInfo(name = "amount_added")
    var amountAdded: Int,

    @ColumnInfo(name = "price")
    var price: Int? = 0,

    @ColumnInfo(name = "image")
    var image: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}