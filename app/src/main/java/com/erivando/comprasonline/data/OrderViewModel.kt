package com.erivando.comprasonline.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erivando.comprasonline.data.OrderViewModel
import com.erivando.comprasonline.data.Order
import com.erivando.comprasonline.data.Product
import com.erivando.comprasonline.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderViewModel (private val repository: DatabaseHelper.ProductRepository)
OrderViewModel(){
    private val _products = MutableLiveData<List<Product>>()
    val products:LiveData<List<Product>> = _products

    fun loadProducts() {
        handleApiCall({ repository.getAllProducts() }, _products)
    }

}

fun saveOrder(order: Order) {
    viewModelScope.launch {
        try {
            setLoading(true)
            repository.saveOrder(order, listOf())
            setSuccess("Pedido salvo com sucesso!")
        } catch (e: Exception) {
            setError(e.message)
        } finally {
            setLoading(false)
        }
    }
}

