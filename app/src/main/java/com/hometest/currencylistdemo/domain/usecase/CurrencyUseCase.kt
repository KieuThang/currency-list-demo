package com.hometest.currencylistdemo.domain.usecase

import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.repository.CurrencyRepository
import com.hometest.currencylistdemo.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {

    inner class LoadCurrencyList() : SingleUseCase<ArrayList<CurrencyInfo>>() {
        override fun buildUseCaseSingle(): Single<ArrayList<CurrencyInfo>> {
            return currencyRepository.loadCurrencyList()
        }
    }

    inner class InsertCurrencyListToDatabase : SingleUseCase<Boolean>() {
        override fun buildUseCaseSingle(): Single<Boolean> {
            return currencyRepository.insertCurrencyListToDatabase()
        }
    }
}