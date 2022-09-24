package com.application.cryptomaniac.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.application.cryptomaniac.CryptoViewModel
import com.application.cryptomaniac.R
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.FragmentListCryptoBinding
import com.application.cryptomaniac.ui.CryptoAdapter
import com.application.cryptomaniac.ui.MainActivity

class CryptoListFragment : BaseFragment() {

    private var binding: FragmentListCryptoBinding? = null
    private var cryptoAdapter: CryptoAdapter? = null

    private lateinit var viewModel: CryptoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            CryptoViewModel.factory((activity as? MainActivity)?.getRepository())
        )[CryptoViewModel::class.java]

        binding = FragmentListCryptoBinding.inflate(inflater, container, false)
        initAdapter()
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        binding?.recyclerView?.adapter = cryptoAdapter

        viewModel.cryptoList.observe(
            viewLifecycleOwner
        ) { list ->
            list?.let {
                cryptoAdapter?.data = list
            }
        }

        changeSelectedState()


    }

    private fun initAdapter() {
        cryptoAdapter = CryptoAdapter(
            object : CryptoAdapter.CryptoClickListener {
                override fun onItemClick(item: Crypto?) {
                    replaceFragment(CryptoDetailsFragment())
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
            binding?.eurChip?.isSelected?.let { selected -> viewModel.isUsd.set(selected) }
            viewModel.getCurrentList()
        }

        binding?.usdChip?.setOnClickListener {
            binding?.usdChip?.isSelected = true
            binding?.eurChip?.isSelected = false
            binding?.eurChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.isSelected?.let { selected -> viewModel.isUsd.set(selected) }
            viewModel.getCurrentList()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}