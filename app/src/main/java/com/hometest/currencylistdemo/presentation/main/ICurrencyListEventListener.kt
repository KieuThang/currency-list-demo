package com.hometest.currencylistdemo.presentation.main

import com.hometest.currencylistdemo.domain.model.CurrencyInfo

interface ICurrencyListEventListener {
    fun selectItem(currencyInfo: CurrencyInfo)

    fun refreshData()

    fun selectSort()

    fun searchCurrency(searchText: String?)
}