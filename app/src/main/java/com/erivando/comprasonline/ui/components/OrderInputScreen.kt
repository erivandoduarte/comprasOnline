package com.erivando.comprasonline.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erivando.comprasonline.domain.Order
import com.erivando.comprasonline.domain.Product
import com.erivando.comprasonline.ui.viewmodels.OrderViewModel

@Composable
fun OrderInputScreen(orderViewModel: OrderViewModel) {
    val orderCodeState = remember { mutableStateOf("") }
    val products = orderViewModel.products.value ?: listOf()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = orderCodeState.value,
            onValueChange = { orderCodeState.value = it },
            label = { Text("Código do Pedido") }
        )

        LazyColumn {
            items(products) { product ->
                ProductItem(product)
            }
        }

        Button(onClick = { orderViewModel.saveOrder(Order(0, orderCodeState.value)) }) {
            Text("Salvar Pedido")
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Text(text = product.name)
}