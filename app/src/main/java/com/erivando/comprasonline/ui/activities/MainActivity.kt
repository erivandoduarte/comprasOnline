package com.erivando.comprasonline.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.erivando.comprasonline.bancoDeDados.DatabaseHelper
import com.erivando.comprasonline.domain.ProductRepository
import com.erivando.comprasonline.ui.components.OrderInputScreen
import com.erivando.comprasonline.ui.viewmodels.OrderViewModel

class MainActivity : ComponentActivity() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = DatabaseHelper(this)
        val repository = ProductRepository(dbHelper)
        orderViewModel = OrderViewModel(repository)

        setContent {
            OrderInputScreen(orderViewModel)
        }

        orderViewModel.loadProducts()
    }
}