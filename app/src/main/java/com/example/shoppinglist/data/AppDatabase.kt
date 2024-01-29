package com.example.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object {

        private const val DB_NAME = "shop_item.db"

        private val Lock = Any()

        private var Instance: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {
            Instance?.let {
                return it
            }
            synchronized(Lock) {
                Instance?.let {
                    return it
                }
                val db = Room
                    .databaseBuilder(application, AppDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
                Instance = db
                return db
            }
        }
    }
}