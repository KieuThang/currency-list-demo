package com.hometest.currencylistdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hometest.currencylistdemo.domain.usecase.CurrencyUseCase
import com.hometest.currencylistdemo.presentation.main.DemoViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DemoViewModelTest {

    lateinit var viewModel: DemoViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CurrencyUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = DemoViewModel(usecase)
    }

    @Test
    fun testSetupData_ReturnTrue(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel.setupData(context = appContext)
        Thread.sleep(1000)

        assertTrue(viewModel.getCurrencyList().size == 14)
    }

    @Test
    fun testSetupData_ReturnFalse(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel.setupData(context = appContext)
        Thread.sleep(1000)

        assertFalse(viewModel.getCurrencyList().size == 0)
    }

    @Test
    fun testLoadCurrencyList_ReturnTrue(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel.loadCurrencyList(context = appContext)
        Thread.sleep(1000)

        assertTrue(viewModel.getCurrencyList().size == 14)
    }
}