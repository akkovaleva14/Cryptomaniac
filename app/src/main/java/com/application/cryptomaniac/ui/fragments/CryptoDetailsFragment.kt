package com.application.cryptomaniac.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.cryptomaniac.databinding.FragmentDetailsCryptoBinding

class CryptoDetailsFragment : BaseFragment() {

    private var binding: FragmentDetailsCryptoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsCryptoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

}