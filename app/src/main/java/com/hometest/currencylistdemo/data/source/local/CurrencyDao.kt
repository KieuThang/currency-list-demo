package com.hometest.currencylistdemo.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CurrencyInfoEntity): Long

    @Query("SELECT * FROM `currency-info`")
    fun loadAll(): MutableList<CurrencyInfoEntity>
}