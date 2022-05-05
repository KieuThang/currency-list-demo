package com.hometest.currencylistdemo.domain.repository

import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import io.reactivex.Single

interface CurrencyRepository {
    fun loadCurrencyList(): Single<ArrayList<CurrencyInfo>>

    fun insertCurrencyListToDatabase(): Single<Boolean>
}