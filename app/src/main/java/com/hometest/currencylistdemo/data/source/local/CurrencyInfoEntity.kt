package com.hometest.currencylistdemo.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "currency-info")
class CurrencyInfoEntity (
    @PrimaryKey
    @NotNull
    var id: String = "",
    var name: String = "",
    var symbol: String = ""
)