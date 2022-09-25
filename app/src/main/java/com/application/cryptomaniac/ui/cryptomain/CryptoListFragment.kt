package com.application.cryptomaniac.ui.cryptomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.application.cryptomaniac.R
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.FragmentListCryptoBinding
import com.application.cryptomaniac.ui.MainActivity
import com.application.cryptomaniac.ui.base.BaseFragment
import com.application.cryptomaniac.ui.cryptodetails.CryptoDetailsFragment
import com.application.cryptomaniac.ui.cryptomain.cryptoadapter.CryptoAdapter

class CryptoListFragment : BaseFragment() {

    private var binding: FragmentListCryptoBinding? = null
    private var cryptoAdapter: CryptoAdapter? = null
    private lateinit var viewModel: CryptoMainViewModel

    var eurIsSelected: Boolean = false

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

    override fun onResume() {
        super.onResume()

        binding?.recyclerView?.adapter = cryptoAdapter
        viewModel.getCurrentList()

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressLoader?.isVisible = isLoading
        }

        viewModel.cryptoList.observe(
            viewLifecycleOwner
        ) { list ->

            list?.let {
                cryptoAdapter?.data = list
                cryptoAdapter?.isUsd = viewModel.isUsd.get()
            }
        }
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
            binding?.eurChip?.isSelected?.let { selected -> viewModel.isUsd.set(!selected) }
            eurIsSelected = true
            viewModel.getCurrentList()
        }

        binding?.usdChip?.setOnClickListener {
            binding?.usdChip?.isSelected = true
            binding?.eurChip?.isSelected = false
            binding?.eurChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.isSelected?.let { selected -> viewModel.isUsd.set(selected) }
            eurIsSelected = false
            viewModel.getCurrentList()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}