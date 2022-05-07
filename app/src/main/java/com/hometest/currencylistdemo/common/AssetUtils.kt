package com.hometest.currencylistdemo.common

import android.content.Context
import com.google.gson.Gson
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import java.io.InputStream

class AssetUtils {
    companion object {
        fun loadCurrencyFromAssets(context: Context, assetFileName: String): ArrayList<CurrencyInfo> {
            return try {
                val inputStream: InputStream = context.assets.open(assetFileName)
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

}