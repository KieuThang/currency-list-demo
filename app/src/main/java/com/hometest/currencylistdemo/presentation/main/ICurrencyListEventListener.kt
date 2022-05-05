package com.hometest.currencylistdemo.presentation.main

import com.hometest.currencylistdemo.domain.model.CurrencyInfo

interface IListEventListener {
    fun selectItem(position: CurrencyInfo)

    fun refreshData()
}