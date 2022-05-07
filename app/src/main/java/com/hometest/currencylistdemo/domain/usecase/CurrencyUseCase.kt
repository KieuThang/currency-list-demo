package com.hometest.currencylistdemo.domain.usecase

import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.repository.CurrencyRepository
import com.hometest.currencylistdemo.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * This class define the business use cases of Currency. This is the main UseCase class in the domain layer of Clean Architecture.
 * This class will be called from the ViewModel in the presentation layer and request the data layer to query the data.
 */
class CurrencyUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {

    inner class LoadCurrencyList : SingleUseCase<ArrayList<CurrencyInfo>>() {
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