package com.example.roorkeesevafpo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roorkeesevafpo.model.local.Item

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item : Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingItems() : LiveData<List<Item>>

    @Query("SELECT SUM(price) FROM shopping_list")
    fun getTotalPrice() : LiveData<Int>

}