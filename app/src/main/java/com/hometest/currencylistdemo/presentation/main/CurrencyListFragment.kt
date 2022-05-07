package com.hometest.currencylistdemo.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AppLog
import com.hometest.currencylistdemo.common.ViewPressEffectHelper
import com.hometest.currencylistdemo.databinding.FragmentCurrencyListBinding
import com.hometest.currencylistdemo.databinding.LayoutCurrencyItemBinding
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.presentation.common.BaseFragment

class CurrencyListFragment : BaseFragment() {
    companion object {
        const val MSG_SEARCH_TEXT = 1245
    }

    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!

    private var currencyList = ArrayList<CurrencyInfo>()
    private var mListener: ICurrencyListEventListener? = null
    private var isSorted = false

    private var mAdapter: CurrencyAdapter? = null
    private var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_SEARCH_TEXT) {
                val now = System.currentTimeMillis()
                if (now - mLastTimeSearch > 500) {
                    AppLog.d(AppConstants.TAG, "START search $searchText")
                    mListener?.searchCurrency(searchText)
                }
            }
        }
    }

    private var mLastTimeSearch = 0L
    private var searchText: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        ViewPressEffectHelper.attach(binding.toolbar.btnSort)
        ViewPressEffectHelper.attach(binding.btnRetry)

        mAdapter = CurrencyAdapter()
        binding.rvCurrency.adapter = mAdapter

        binding.swipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({ mListener?.refreshData() }, 500)
        }

        binding.btnRetry.setOnClickListener {
            mListener?.refreshData()
        }

        binding.toolbar.btnSort.setOnClickListener {
            mListener?.selectSort()
        }

        binding.toolbar.edtSearch.doAfterTextChanged {
            AppLog.d(AppConstants.TAG, "doAfterTextChanged ${it.toString()}")
            searchText = it.toString()
            mLastTimeSearch = System.currentTimeMillis()

            val msg = mHandler.obtainMessage()
            msg.what = MSG_SEARCH_TEXT
            mHandler.sendMessageDelayed(msg, 550)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrencyList(data: ArrayList<CurrencyInfo>?, isSorted: Boolean, listener: ICurrencyListEventListener) {
        if (!isAdded) {
            return
        }
        if(data == null){
            binding.swipeRefresh.visibility = View.GONE
            binding.layoutError.visibility = View.VISIBLE
            binding.tvError.text = getString(R.string.failed_to_load_currency_information)
            return
        }
        currencyList = data
        mListener = listener
        this.isSorted = isSorted
        binding.swipeRefresh.isRefreshing = false
        if (currencyList.size == 0) {
            binding.swipeRefresh.visibility = View.GONE
            binding.layoutError.visibility = View.VISIBLE
            binding.tvError.text = getString(R.string.no_currency_information_found)
        } else {
            binding.swipeRefresh.visibility = View.VISIBLE
            binding.layoutError.visibility = View.GONE
        }

        binding.toolbar.btnSort.setImageResource(if (isSorted) R.drawable.ic_filter_active else R.drawable.ic_filter_normal)
        mAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
        private val inflater: LayoutInflater = LayoutInflater.from(requireActivity())

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.ViewHolder {
            val binding = LayoutCurrencyItemBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CurrencyAdapter.ViewHolder, position: Int) {
            holder.bind(currencyList[position])
        }

        override fun getItemCount(): Int {
            return currencyList.size
        }

        inner class ViewHolder(private val itemBinding: LayoutCurrencyItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(currencyInfo: CurrencyInfo) {
                itemBinding.tvChar.text = currencyInfo.name[0].uppercase()
                itemBinding.tvName.text = currencyInfo.name
                itemBinding.tvSymbol.text = currencyInfo.symbol
                itemBinding.root.setOnClickListener {
                    mListener?.selectItem(currencyInfo)
                }
            }
        }
    }
}