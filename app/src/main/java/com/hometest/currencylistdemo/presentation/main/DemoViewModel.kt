package com.hometest.currencylistdemo.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.SharePrefUtils
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.domain.usecase.CurrencyUseCase
import com.hometest.currencylistdemo.presentation.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {

    private val _currencyInfoListEvent = MutableLiveData<Resource<ArrayList<CurrencyInfo>>>()
    val currencyInfoList: LiveData<Resource<ArrayList<CurrencyInfo>>>
        get() = _currencyInfoListEvent

    fun setupData(context: Context) {
        val isFirstTimeRunApp =
            SharePrefUtils.getBooleanValue(context, AppConstants.Prefs.DEFAULT_PREFS, AppConstants.Prefs.KEY_FIRST_TIME_RUN_APP, defaultValue = true)
        if (isFirstTimeRunApp) {
            _currencyInfoListEvent.postValue(Resource.loading(null))
            currencyUseCase.InsertCurrencyListToDatabase().execute(
                onSuccess = {
                    if(!it){
                        _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
                        return@execute
                    }
                    loadCurrencyList(context)
                },

                onError = {
                    _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
                }
            )
        }else{
            loadCurrencyList(context)
        }
    }

    fun loadCurrencyList(context: Context) {
        currencyUseCase.LoadCurrencyList().execute(
            onSuccess = {
                _currencyInfoListEvent.postValue(Resource.success(it))
            },
            onError = {
                _currencyInfoListEvent.postValue(Resource.error(context.getString(R.string.failed_to_load_currency_information), null))
            }
        )
    }

    fun sortCurrencyList() {

    }
}