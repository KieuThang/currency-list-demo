package com.hometest.currencylistdemo.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.databinding.ActivityMainBinding
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: DemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupDataEvents()
//        setSupportActionBar(binding.toolbar)
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupDataEvents() {
        viewModel.currencyInfoList.observe(this) {
            val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            val currencyListFragment = navHostFragment?.childFragmentManager?.fragments?.get(0) as CurrencyListFragment?

            if(currencyListFragment == null || it.data == null || it.data.size == 0){
                return@observe
            }
            currencyListFragment.setCurrencyList(it.data, object : IListEventListener {
                override fun selectItem(position: CurrencyInfo) {

                }

                override fun refreshData() {
                    viewModel.loadCurrencyList(this@DemoActivity)
                }
            })
        }
    }

    private fun setupData() {
        viewModel.setupData(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}