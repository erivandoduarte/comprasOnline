package com.erivando.comprasonline.ui.theme

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
import com.erivando.comprasonline.data.OrderItem
import com.erivando.comprasonline.data.Product

@Composable
fun OrderInputScreen(orderViewModel: OrderViewModel) {
    val orderCodeState = remember {
        mutableStateOf("")
    }
    val products = orderViewModel.products.value ?: listOf()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = orderCodeState.value,
            onValueChange = {orderCodeState.value=it},
            label = {Text("Código do pedido")}
        )
    }

    LazyColumn {
        items(products) {product ->
            ProductItem(product, orderViewModel::addOrderItem)
        }
    }

    Button(onClick = {orderViewModel.saveOrder(orderCodeState.value) }) {
        Text("Salvar Pedido")
    }
}

@Composable
fun ProductItem(product: Product, onAddItem: (Product,Int) -> Unit){
    val quantityState = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = product.name)
        TextField(
            value = quantityState.value,
            onValueChange = {quantityState.value=it},
            label = {Text("Quantidade")}
        )
        Button(onClick = {onAddItem(product,quantityState.value.toInt())}) {
            Text("Adicionar")
        }
    }


}