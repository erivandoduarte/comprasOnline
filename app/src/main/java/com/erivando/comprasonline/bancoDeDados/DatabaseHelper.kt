package com.erivando.comprasonline.bancoDeDados

// DatabaseHelper.kt
import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
}