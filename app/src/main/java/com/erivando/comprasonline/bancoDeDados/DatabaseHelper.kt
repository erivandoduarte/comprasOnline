package com.erivando.comprasonline.bancoDeDados

// DatabaseHelper.kt
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.erivando.comprasonline.data.Order
import com.erivando.comprasonline.data.OrderItem
import com.erivando.comprasonline.data.Product

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY, name TEXT NOT NULL)")
        db.execSQL("CREATE TABLE prices (id INTEGER PRIMARY KEY, product_id INTEGER, price REAL, FOREIGN KEY(product_id) REFERENCES products(id))")
        db.execSQL("CREATE TABLE orders (order_id INTEGER PRIMARY KEY, order_code TEXT NOT NULL)")
        db.execSQL("CREATE TABLE order_items (id INTEGER PRIMARY KEY, order_id INTEGER, product_id INTEGER, quantity INTEGER, FOREIGN KEY(order_id) REFERENCES orders(order_id), FOREIGN KEY(product_id) REFERENCES products(id))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL("DROP TABLE IF EXISTS prices")
        db.execSQL("DROP TABLE IF EXISTS orders")
        db.execSQL("DROP TABLE IF EXISTS order_items")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "sales.db"
        private const val DATABASE_VERSION = 1
    }
}

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

