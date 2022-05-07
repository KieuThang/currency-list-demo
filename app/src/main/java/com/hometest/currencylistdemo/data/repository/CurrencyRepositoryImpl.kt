package com.hometest.currencylistdemo.data.repository

import android.content.Context
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AssetUtils
import com.hometest.currencylistdemo.common.SharePrefUtils
import com.hometest.currencylistdemo.data.common.toEntity
import com.hometest.currencylistdemo.data.source.local.AppDatabase
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * This class will implement feature related to currency business logic such as loadCurrencyList, insertCurrencyListToDatabase,...
 * Based on the specific logic, this #CurrencyRepositoryImpl can query the data from the local or remote resources.
 */
class CurrencyRepositoryImpl @Inject constructor(private val context: Context, private val appDatabase: AppDatabase) : CurrencyRepository {
    override fun loadCurrencyList(): Single<ArrayList<CurrencyInfo>> {
        return Single.create { subscriber ->
            val currencyInfoList = appDatabase.currencyDao.loadAll()
            val currencyList = ArrayList<CurrencyInfo>()
            currencyInfoList.forEach { entity ->
                val currencyInfo = CurrencyInfo(entity.id, entity.name, entity.symbol)
                currencyList.add(currencyInfo)
            }
            subscriber.onSuccess(currencyList)
        }
    }

    override fun insertCurrencyListToDatabase(): Single<Boolean> {
        return Single.create { subscriber ->
            val currencyList = AssetUtils.loadCurrencyFromAssets(context, AppConstants.ASSET_FILE_NAME)
            if (currencyList.size == 0) {
                subscriber.onSuccess(false)
                return@create
            }
            currencyList.forEach { currency ->
                appDatabase.currencyDao.insert(currency.toEntity())
            }

            SharePrefUtils.putBooleanValue(context, AppConstants.Prefs.DEFAULT_PREFS, AppConstants.Prefs.KEY_FIRST_TIME_RUN_APP, false)
            subscriber.onSuccess(true)
        }
    }
}