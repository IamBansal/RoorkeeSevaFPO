package com.example.roorkeesevafpo.model.remote

data class DataProduct(
    val __v: Int,
    val _id: String,
    val category: String,
    val createdAt: String,
    val current_quantity: String,
    val fpo_id: String,
    val multiple_photo_link: List<Any>,
    val original_price: String,
    val product: String,
    val quantity_added: String,
    val selling_price: String,
    val updatedAt: String
) : java.io.Serializable