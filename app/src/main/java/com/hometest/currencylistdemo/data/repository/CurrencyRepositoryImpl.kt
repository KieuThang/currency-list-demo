package com.hometest.currencylistdemo.data.repository

import android.content.Context
import com.google.gson.Gson
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AppLog
import com.hometest.currencylistdemo.common.SharePrefUtils
import com.hometest.currencylistdemo.data.common.toEntity
import com.hometest.currencylistdemo.data.source.local.AppDatabase
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.repository.CurrencyRepository
import io.reactivex.Single
import java.io.InputStream
import javax.inject.Inject

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
            val currencyList = loadCurrencyFromAssets()
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

    private fun loadCurrencyFromAssets(): ArrayList<CurrencyInfo> {
        return try {
            val inputStream: InputStream = context.assets.open("currency_list.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer)
            if (json.isEmpty()) {
                return ArrayList()
            }
            val list = Gson().fromJson(json, Array<CurrencyInfo>::class.java).asList()
            return ArrayList(list)
        } catch (exception: Exception) {
            exception.printStackTrace()
            AppLog.e(AppConstants.TAG, "can not read file: ${exception.message}")
            ArrayList()
        }
    }
}