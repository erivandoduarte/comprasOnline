package com.erivando.comprasonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.erivando.comprasonline.bancoDeDados.DatabaseHelper
import com.erivando.comprasonline.bancoDeDados.ProductRepository
import com.erivando.comprasonline.ui.theme.ComprasOnlineTheme
import com.erivando.comprasonline.ui.theme.OrderInputScreen
import com.erivando.comprasonline.ui.theme.OrderViewModel

class MainActivity : ComponentActivity() {
    private val orderViewModel: OrderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = DatabaseHelper(this)
        val repository = ProductRepository(dbHelper)
        val orderViewModel = OrderViewModel(repository)

        setContent {
            ComprasOnlineTheme {
                Surface(color = MaterialTheme.colorScheme.background)
                {
                    Greeting(orderViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(orderViewModel: OrderViewModel) {
    OrderInputScreen(orderViewModel)
}

