package com.erivando.comprasonline.domain
import android.content.ContentValues
import com.erivando.comprasonline.bancoDeDados.DatabaseHelper

class ProductRepository(private val dbHelper: DatabaseHelper) {

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM products", null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("id"))
                val name = it.getString(it.getColumnIndexOrThrow("name"))
                val price = it.getDouble(it.getColumnIndexOrThrow("price"))
                products.add(Product(id, name, price))
            }
        }
        return products
    }

    fun saveOrder(order: Order, items: List<OrderItem>) {
        val db = dbHelper.writableDatabase
        val orderId = db.insert("orders", null, ContentValues().apply {
            put("order_code", order.orderCode)
        })

        items.forEach { item ->
            db.insert("order_items", null, ContentValues().apply {
                put("order_id", orderId)
                put("product_id", item.productId)
                put("quantity", item.quantity)
            })
        }
    }
}