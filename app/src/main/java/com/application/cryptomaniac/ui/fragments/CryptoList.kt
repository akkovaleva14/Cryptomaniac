package com.application.cryptomaniac.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.cryptomaniac.databinding.ListCryptoBinding

class CryptoList : Fragment() {

    private var binding: ListCryptoBinding? = null
    //  val cryptoAdapter = CryptoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListCryptoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        // binding.recyclerView.adapter = cryptoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}