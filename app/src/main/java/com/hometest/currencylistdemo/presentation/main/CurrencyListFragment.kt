package com.hometest.currencylistdemo.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hometest.currencylistdemo.databinding.FragmentCurrencyListBinding
import com.hometest.currencylistdemo.databinding.LayoutCurrencyItemBinding
import com.hometest.currencylistdemo.domain.model.CurrencyInfo
import com.hometest.currencylistdemo.presentation.common.BaseFragment

class CurrencyListFragment : BaseFragment() {

    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!

    private var currencyList = ArrayList<CurrencyInfo>()
    private var mListener: IListEventListener? = null

    private var mAdapter: CurrencyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        setupUI()
    }

    private fun setupUI() {
        setupToolbar()

        mAdapter = CurrencyAdapter()
        binding.rvCurrency.adapter = mAdapter

        binding.swipeRefresh.setOnRefreshListener {
            mListener?.refreshData()
        }

        binding.btnRetry.setOnClickListener {
            mListener?.refreshData()
        }
    }

    private fun setupToolbar() {
//        ViewPressEffectHelper.attach(binding.toolbar.imgFilter)

        binding.toolbar.imgFilter.setOnClickListener {

        }
    }

    fun setCurrencyList(data: ArrayList<CurrencyInfo>, listener: IListEventListener) {
        currencyList = data
        mListener = listener
        if (!isAdded) {
            return
        }
        binding.swipeRefresh.isRefreshing = false
        if (data.size == 0) {
            binding.swipeRefresh.visibility = View.GONE
            binding.layoutError.visibility = View.VISIBLE
        }else{
            binding.swipeRefresh.visibility = View.VISIBLE
            binding.layoutError.visibility = View.GONE
        }
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