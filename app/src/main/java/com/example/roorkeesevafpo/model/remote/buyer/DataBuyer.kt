package com.example.roorkeesevafpo.model.remote.buyer

data class DataBuyer(
    val __v: Int,
    val _id: String,
    val address: Address,
    val cart: List<Any>,
    val createdAt: String,
    val email: String,
    val name: String,
    val notifications: List<Any>,
    val orders: List<String>,
    val phone_no: String,
    val photo: String,
    val shops: List<Any>,
    val updatedAt: String
)