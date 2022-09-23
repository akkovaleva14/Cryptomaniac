package com.application.cryptomaniac

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.cryptomaniac.databinding.ItemCryptoBinding

class CryptoHolder(private val binding: ItemCryptoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): CryptoHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCryptoBinding.inflate(inflater, parent, false)
            return CryptoHolder(binding)
        }
    }

}

