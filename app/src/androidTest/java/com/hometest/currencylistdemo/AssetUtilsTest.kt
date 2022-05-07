package com.hometest.currencylistdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AssetUtils
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AssetUtilsTest {

    @Test
    fun loadDataFromAsset_ReturnTrue() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val currencyList = AssetUtils.loadCurrencyFromAssets(appContext, AppConstants.ASSET_FILE_NAME)
        assertEquals(currencyList.size, 14)
    }

    @Test
    fun loadDataFromAsset_ReturnFalse() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val currencyList = AssetUtils.loadCurrencyFromAssets(appContext, "test.json")
        assertFalse(currencyList.size == 14)
    }
}