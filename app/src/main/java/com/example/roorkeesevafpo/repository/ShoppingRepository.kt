package com.example.roorkeesevafpo.repository

import com.example.roorkeesevafpo.api.RetrofitInstance
import com.example.roorkeesevafpo.db.ShoppingDatabase
import com.example.roorkeesevafpo.model.local.Item
import com.example.roorkeesevafpo.model.remote.DataProduct

class ShoppingRepository(
    private val db: ShoppingDatabase
) {

    suspend fun upsert(item: Item) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: Item) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()

    fun getTotalPrice() = db.getShoppingDao().getTotalPrice()

    suspend fun getFpo() = RetrofitInstance.api.getFPO()

    suspend fun getProducts() = RetrofitInstance.api.getProduct()

    suspend fun getBuyers() = RetrofitInstance.api.getBuyer()

    suspend fun updateProduct(id: String, product: DataProduct) = RetrofitInstance.api.updateProduct(id, product)

}