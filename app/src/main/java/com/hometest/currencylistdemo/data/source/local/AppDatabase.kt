package com.hometest.currencylistdemo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val currencyDao: CurrencyDao

    companion object {
        const val DB_NAME = "CurrencyDemo.db"
    }
}
