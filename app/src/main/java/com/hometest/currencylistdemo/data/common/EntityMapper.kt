package com.hometest.currencylistdemo.data.common
import com.hometest.currencylistdemo.data.source.local.CurrencyInfoEntity
import com.hometest.currencylistdemo.domain.model.CurrencyInfo

fun CurrencyInfo.toEntity() = CurrencyInfoEntity(
    id = id ,
    name = name,
    symbol = symbol
)


