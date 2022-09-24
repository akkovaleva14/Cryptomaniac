package com.application.cryptomaniac.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.cryptomaniac.R
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.ListCryptoBinding
import com.application.cryptomaniac.ui.CryptoAdapter

class CryptoListFragment : BaseFragment() {

    private var binding: ListCryptoBinding? = null
    private var cryptoAdapter: CryptoAdapter? = null
    /* private val usdChipBinding = binding?.usdChip
     private val eurChipBinding = binding?.eurChip*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListCryptoBinding.inflate(inflater, container, false)
        initAdapter()
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        binding?.recyclerView?.adapter = cryptoAdapter

        binding?.usdChip?.isSelected = true
        binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)

        binding?.eurChip?.setOnClickListener {
            binding?.usdChip?.isSelected = false
            binding?.eurChip?.isSelected = true
            binding?.eurChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.platinum)
        }

        binding?.usdChip?.setOnClickListener {
            binding?.usdChip?.isSelected = true
            binding?.eurChip?.isSelected = false
            binding?.eurChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)
        }

        /* НЕ РАБОТАЕТ

        usdChipBinding?.isSelected = true
        usdChipBinding?.setChipBackgroundColorResource(R.color.antique_white)

        eurChipBinding?.setOnClickListener {
            usdChipBinding?.isSelected = false
            eurChipBinding?.isSelected = true
            eurChipBinding?.setChipBackgroundColorResource(R.color.antique_white)
            usdChipBinding?.setChipBackgroundColorResource(R.color.platinum)
        }

        usdChipBinding?.setOnClickListener {
            usdChipBinding?.isSelected = true
            eurChipBinding?.isSelected = false
            eurChipBinding?.setChipBackgroundColorResource(R.color.platinum)
            usdChipBinding?.setChipBackgroundColorResource(R.color.antique_white)
        }*/

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


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}