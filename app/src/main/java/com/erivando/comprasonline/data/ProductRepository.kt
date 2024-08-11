package com.erivando.comprasonline.data
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.erivando.comprasonline.bancoDeDados.DatabaseHelper
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY, name TEXT NOT NULL, price REAL NOT NULL)")
        db.execSQL("CREATE TABLE orders (order_id INTEGER PRIMARY KEY, order_code TEXT NOT NULL)")
        db.execSQL("CREATE TABLE order_items (id INTEGER PRIMARY KEY, order_id INTEGER, product_id INTEGER, quantity INTEGER, FOREIGN KEY(order_id) REFERENCES orders(order_id), FOREIGN KEY(product_id) REFERENCES products(id))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL("DROP TABLE IF EXISTS orders")
        db.execSQL("DROP TABLE IF EXISTS order_items")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "order_app.db"
        private const val DATABASE_VERSION = 1
    }


class ProductRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor = db.query("products", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                )
                products.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return products
    }

    fun insertOrder(order: Order, items: List<OrderItem>) {
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

    fun getOrderWithItems(orderId: Int): List<OrderItem> {
        val orderItems = mutableListOf<OrderItem>()
        val cursor = db.query("order_items", null, "order_id = ?", arrayOf(orderId.toString()), null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val orderItem = OrderItem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    orderId = cursor.getInt(cursor.getColumnIndexOrThrow("order_id")),
                    productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
                )
                orderItems.add(orderItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderItems
    }
}

}