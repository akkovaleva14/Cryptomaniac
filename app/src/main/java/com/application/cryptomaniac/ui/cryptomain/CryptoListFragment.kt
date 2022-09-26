package com.application.cryptomaniac.ui.cryptomain

import android.os.Bundle
import android.util.Log
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
import com.application.cryptomaniac.utils.isOnline

class CryptoListFragment : BaseFragment() {

    private var binding: FragmentListCryptoBinding? = null
    private var cryptoAdapter: CryptoAdapter? = null
    private lateinit var viewModel: CryptoMainViewModel

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

        if (isOnline(requireContext())) {
            binding?.error?.root?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
            viewModel.getCurrentList()
            Log.e("isOnline", "ON")
        } else {
            binding?.error?.buttonError?.setOnClickListener { viewModel.getCurrentList() }
            binding?.error?.root?.visibility = View.VISIBLE
            binding?.recyclerView?.visibility = View.GONE
            Log.e("isOnline", "OFF")
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

            if (isOnline(requireContext())) {
                binding?.error?.root?.visibility = View.GONE
                binding?.recyclerView?.visibility = View.VISIBLE
                viewModel.getCurrentList()
                Log.e("BUTTON", "Btn clicked. ON")
            } else {
                // проблема в setOnClickListener { viewModel.getCurrentList() }
                binding?.error?.buttonError?.setOnClickListener { viewModel.getCurrentList() }
                binding?.error?.root?.visibility = View.VISIBLE
                binding?.recyclerView?.visibility = View.GONE
                Log.e("BUTTON", "Btn clicked. OFF")
            }
        }

        binding?.usdChip?.setOnClickListener {
            binding?.usdChip?.isSelected = true
            binding?.eurChip?.isSelected = false
            binding?.eurChip?.setChipBackgroundColorResource(R.color.platinum)
            binding?.usdChip?.setChipBackgroundColorResource(R.color.antique_white)
            binding?.usdChip?.isSelected?.let { selected -> viewModel.isUsd.set(selected) }

            if (isOnline(requireContext())) {
                binding?.error?.root?.visibility = View.GONE
                binding?.recyclerView?.visibility = View.VISIBLE
                viewModel.getCurrentList()
                Log.e("BUTTON", "Btn clicked. ON")
            } else {
                binding?.error?.buttonError?.setOnClickListener { viewModel.getCurrentList() }
                binding?.error?.root?.visibility = View.VISIBLE
                binding?.recyclerView?.visibility = View.GONE
                Log.e("BUTTON", "Btn clicked. OFF")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}