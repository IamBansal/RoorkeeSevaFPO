package com.example.roorkeesevafpo.api

import com.example.roorkeesevafpo.model.remote.DataProduct
import com.example.roorkeesevafpo.model.remote.FPO
import com.example.roorkeesevafpo.model.remote.Product
import com.example.roorkeesevafpo.model.remote.buyer.Buyer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("fpo")
    suspend fun getFPO() : Response<FPO>

    @GET("product")
    suspend fun getProduct() : Response<Product>

    @GET("buyer")
    suspend fun getBuyer() : Response<Buyer>

    @POST("product/{product_id}")
    suspend fun updateProduct(
        @Path(value = "product_id") product_id : String,
        @Body product: DataProduct
    )
}