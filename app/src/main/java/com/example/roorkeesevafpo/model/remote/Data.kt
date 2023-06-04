package com.example.roorkeesevafpo.model.remote

data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val image: String,
    val name: String,
    val number: String,
    val orders: List<String>,
    val product_items: List<String>,
    val products: List<String>,
    val rating: String,
    val total_product_sold: String,
    val updatedAt: String,
    val upi_id: String
)