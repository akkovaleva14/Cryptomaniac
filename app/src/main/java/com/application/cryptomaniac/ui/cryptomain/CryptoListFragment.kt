package com.application.cryptomaniac.ui.cryptomain

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.application.cryptomaniac.R
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.FragmentListCryptoBinding
import com.application.cryptomaniac.ui.MainActivity
import com.application.cryptomaniac.ui.base.BaseFragment
import com.application.cryptomaniac.ui.cryptodetails.CryptoDetailsFragment
import com.application.cryptomaniac.ui.cryptomain.cryptoadapter.CryptoAdapter
import com.application.cryptomaniac.utils.isOnline

class CryptoListFragment : BaseFragment() {

    private var binding: FragmentListCryptoBinding? = null
    private var cryptoAdapter: CryptoAdapter? = null
    private var viewModel: CryptoMainViewModel? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            CryptoMainViewModel.factory((activity as? MainActivity)?.getRepository())
        )[CryptoMainViewModel::class.java]

        binding = FragmentListCryptoBinding.inflate(inflater, container, false)
        initAdapter()
        return binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        binding?.error?.buttonError?.setOnClickListener {
            binding?.progressLoader?.visibility = View.VISIBLE
            checkError()
        }

        binding?.recyclerView?.adapter = cryptoAdapter

        cryptoAdapter?.notifyDataSetChanged()
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            // on below line we are setting is refreshing to false.
            swipeRefreshLayout?.isRefreshing = false
            cryptoAdapter?.notifyDataSetChanged()
            //   viewModel?.getCurrentList()
            Log.e("refresh", "refreshed or at least tried")
        }

        viewModel?.isLoading?.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressLoader?.isVisible = isLoading
        }

        viewModel?.cryptoList?.observe(
            viewLifecycleOwner
        ) { list ->
            list?.let {
                cryptoAdapter?.data = list
                cryptoAdapter?.isUsd = viewModel?.isUsd!!.get()
            }
        }

        checkError()

        changeSelectedState()
    }

    private fun initAdapter() {
        cryptoAdapter = CryptoAdapter(
            object : CryptoAdapter.CryptoClickListener {
                override fun onItemClick(item: Crypto?) {
                    replaceFragment(CryptoDetailsFragment.newInstance(item?.id))
                }
            }
        )
    }


    fun changeSelectedState() {
        binding?.usdChip?.isSelected = true
        binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)

        binding?.eurChip?.setOnClickListener {
            binding?.usdChip?.isSelected = false
            binding?.eurChip?.isSelected = true
            binding?.eurChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.eurChip?.isSelected?.let { selected -> viewModel?.isUsd!!.set(!selected) }
            checkError()
        }

        binding?.usdChip?.setOnClickListener {
            binding?.usdChip?.isSelected = true
            binding?.eurChip?.isSelected = false
            binding?.eurChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.isSelected?.let { selected -> viewModel?.isUsd!!.set(selected) }
            checkError()
        }
    }

    private fun checkError() {
        if (isOnline(context)) {
            binding?.error?.root?.visibility = View.GONE
            binding?.swipeRefreshLayout?.visibility = View.VISIBLE
            viewModel?.getCurrentList()

        } else {
            binding?.error?.root?.visibility = View.VISIBLE
            binding?.swipeRefreshLayout?.visibility = View.GONE
            binding?.toolbar?.visibility = View.VISIBLE
            binding?.viewLine?.visibility = View.VISIBLE
            binding?.progressLoader?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}