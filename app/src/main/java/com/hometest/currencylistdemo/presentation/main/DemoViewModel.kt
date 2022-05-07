package com.hometest.currencylistdemo.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.SharePrefUtils
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.usecase.CurrencyUseCase
import com.hometest.currencylistdemo.presentation.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {

    private val _currencyInfoListEvent = MutableLiveData<Resource<ArrayList<CurrencyInfo>>>()
    val currencyInfoListEvent: LiveData<Resource<ArrayList<CurrencyInfo>>>
        get() = _currencyInfoListEvent

    var searchText: String? = null
    var isSorted = false
    var currencyInfoList = ArrayList<CurrencyInfo>()

    fun setupData(context: Context) {
        searchText = null
        isSorted = false
        val isFirstTimeRunApp =
            SharePrefUtils.getBooleanValue(context, AppConstants.Prefs.DEFAULT_PREFS, AppConstants.Prefs.KEY_FIRST_TIME_RUN_APP, defaultValue = true)
        if (isFirstTimeRunApp) {
            _currencyInfoListEvent.postValue(Resource.loading(null))
            currencyUseCase.InsertCurrencyListToDatabase().execute(
                onSuccess = {
                    if (!it) {
                        _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
                        return@execute
                    }
                    loadCurrencyList(context)
                },

                onError = {
                    _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
                }
            )
        } else {
            loadCurrencyList(context)
        }
    }

    fun loadCurrencyList(context: Context) {
        currencyUseCase.LoadCurrencyList().execute(
            onSuccess = {
                currencyInfoList = it
                searchAndSortCurrency(searchText)
            },
            onError = {
                _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
            }
        )
    }

    fun sortCurrencyList() {
        isSorted = !isSorted
        searchAndSortCurrency(searchText)
    }

    fun searchAndSortCurrency(searchText: String?) {
        this.searchText = searchText
        viewModelScope.launch {
            val result = doSearchAndSort(searchText)
            _currencyInfoListEvent.postValue(Resource.success(result.toCollection(ArrayList())))
        }
    }

    private suspend fun doSearchAndSort(searchText: String?): List<CurrencyInfo> = withContext(Dispatchers.Default) {
        var result = ArrayList<CurrencyInfo>()
        if (!searchText.isNullOrEmpty()) {
            currencyInfoList.forEach {
                if (it.name.lowercase().contains(searchText.lowercase()) || it.symbol.lowercase().contains(searchText.lowercase())) {
                    result.add(it)
                }
            }
        } else {
            result = currencyInfoList
        }
        if (isSorted) {
            return@withContext result.sortedWith(compareBy { it.name })
        } else {
            return@withContext result.toCollection(ArrayList())
        }
    }

    fun getCurrencyList(): ArrayList<CurrencyInfo>{
        return currencyInfoList
    }
}