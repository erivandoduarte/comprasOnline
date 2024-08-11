package com.erivando.comprasonline.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erivando.comprasonline.data.DatabaseHelper
import com.erivando.comprasonline.data.Order
import com.erivando.comprasonline.data.OrderItem
import com.erivando.comprasonline.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _orderItens = MutableLiveData<List<OrderItem>>()
    val orderItens: LiveData<List<OrderItem>> = _orderItens

    private val repository = DatabaseHelper.ProductRepository(application)

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO){
            val loadedProducts = repository.getAllProducts()
            _products.postValue(loadedProducts)
        }
    }
    fun addOrderItem(product: Product, quantity: Int){
        val currentItens = _orderItens.value?.toMutableList() ?: mutableListOf()
        currentItens.add(OrderItem(0,0, product.id,quantity))
        _orderItens.value = currentItens
    }
    fun saveOrder(orderCode: String){
        viewModelScope.launch(Dispatchers.IO){
            val order = Order(0,orderCode)
            repository.insertOrder(order,_orderItens.value ?: listOf())
        }
    }

}