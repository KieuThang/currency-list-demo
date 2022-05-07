package com.hometest.currencylistdemo.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AppLog
import com.hometest.currencylistdemo.databinding.ActivityMainBinding
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.presentation.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : BaseActivity(), ICurrencyListEventListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: DemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        viewModel.setupData(this)

        viewModel.currencyInfoListEvent.observe(this) {
            val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            val currencyListFragment = navHostFragment?.childFragmentManager?.fragments?.get(0) as CurrencyListFragment? ?: return@observe

            currencyListFragment.setCurrencyList(it.data, viewModel.isSorted, this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun selectItem(currencyInfo: CurrencyInfo) {
        AppLog.d(AppConstants.TAG, "selectItem: ${currencyInfo.name}")
        showToastMessage(getString(R.string.you_select_currency, currencyInfo.name))
    }

    override fun refreshData() {
        viewModel.loadCurrencyList(this@DemoActivity)
    }

    override fun selectSort() {
        viewModel.sortCurrencyList()
    }

    override fun searchCurrency(searchText: String?) {
        viewModel.searchAndSortCurrency(searchText)
    }
}