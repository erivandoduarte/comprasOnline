package com.erivando.comprasonline.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erivando.comprasonline.base.BaseViewModel
import com.erivando.comprasonline.domain.Order
import com.erivando.comprasonline.domain.Product
import com.erivando.comprasonline.domain.ProductRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: ProductRepository) : BaseViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun loadProducts() {
        handleApiCall({ repository.getAllProducts() }, _products)
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
}