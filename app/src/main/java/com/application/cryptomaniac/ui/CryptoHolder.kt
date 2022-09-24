package com.application.cryptomaniac.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.ItemCryptoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CryptoHolder(private val binding: ItemCryptoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): CryptoHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCryptoBinding.inflate(inflater, parent, false)
            return CryptoHolder(binding)
        }

    }

    fun bind(crypto: Crypto?, clickListener: CryptoAdapter.CryptoClickListener) {

        Glide
            .with(binding.cryptoImage)
            .load(crypto?.image)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.cryptoImage)

        binding.cryptoSymbol.text = crypto?.symbol
        binding.cryptoName.text = crypto?.name
        binding.cryptoCurrentPrice.text = crypto?.currentPrice.toString().format("%.2f")
        binding.cryptoPriceChangePercentage.text =
            crypto?.priceChangePercentage24h.toString().format("%.2f")

        binding.root.setOnClickListener {
            clickListener.onItemClick(crypto)
        }
    }
}
