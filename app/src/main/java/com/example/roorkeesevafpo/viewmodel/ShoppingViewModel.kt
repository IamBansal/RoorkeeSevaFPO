package com.example.roorkeesevafpo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roorkeesevafpo.model.local.Item
import com.example.roorkeesevafpo.model.remote.DataProduct
import com.example.roorkeesevafpo.model.remote.FPO
import com.example.roorkeesevafpo.model.remote.Product
import com.example.roorkeesevafpo.model.remote.buyer.Buyer
import com.example.roorkeesevafpo.repository.ShoppingRepository
import com.example.roorkeesevafpo.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    fun upsert(item: Item) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: Item) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun updateProduct(id: String, product: DataProduct) = viewModelScope.launch {
        repository.updateProduct(id, product)
    }

    val totalItems: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: MutableLiveData<Int> = MutableLiveData(0)

    fun getAllShoppingItems() = repository.getAllShoppingItems()

    fun getTotalPrice() = repository.getTotalPrice()

    val fpo: MutableLiveData<Resource<FPO>> = MutableLiveData()

    fun getFpo() = viewModelScope.launch {
        getSafeFpo()
    }

    private suspend fun getSafeFpo() {

        fpo.postValue(Resource.Loading())
        try {
            val response = repository.getFpo()
            Log.d("response", response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    fpo.postValue(Resource.Success(it))
                }
            } else {
                fpo.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> fpo.postValue(Resource.Error("Network failure"))
//                else -> fpo.postValue(Resource.Error(t.message.toString()))
//            }
            fpo.postValue(Resource.Error(t.message.toString()))
        }

    }

    val products: MutableLiveData<Resource<Product>> = MutableLiveData()

    fun getProduct() = viewModelScope.launch {
        getSafeProduct()
    }

    private suspend fun getSafeProduct() {

        products.postValue(Resource.Loading())
        try {
            val response = repository.getProducts()
            Log.d("response", response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    products.postValue(Resource.Success(it))
                }
            } else {
                products.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> fpo.postValue(Resource.Error("Network failure"))
//                else -> fpo.postValue(Resource.Error(t.message.toString()))
//            }
            products.postValue(Resource.Error(t.message.toString()))
        }

    }


    val buyers: MutableLiveData<Resource<Buyer>> = MutableLiveData()

    fun getBuyer() = viewModelScope.launch {
        getSafeBuyer()
    }

    private suspend fun getSafeBuyer() {

        buyers.postValue(Resource.Loading())
        try {
            val response = repository.getBuyers()
            Log.d("response", response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    buyers.postValue(Resource.Success(it))
                }
            } else {
                buyers.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> fpo.postValue(Resource.Error("Network failure"))
//                else -> fpo.postValue(Resource.Error(t.message.toString()))
//            }
            buyers.postValue(Resource.Error(t.message.toString()))
        }

    }



}