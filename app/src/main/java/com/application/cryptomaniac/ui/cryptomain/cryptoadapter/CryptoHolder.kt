package com.application.cryptomaniac.ui.cryptomain.cryptoadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.cryptomaniac.R
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.databinding.ItemCryptoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.math.BigDecimal
import java.util.*

class CryptoHolder(private val binding: ItemCryptoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): CryptoHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCryptoBinding.inflate(inflater, parent, false)
            return CryptoHolder(binding)
        }
    }

    fun bind(crypto: Crypto?, clickListener: CryptoAdapter.CryptoClickListener, isUsd: Boolean) {

        Glide
            .with(binding.cryptoImage)
            .load(crypto?.image)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.cryptoImage)

        val currencySymbol =
            if (isUsd) "$" else "€"
        binding.cryptoSymbol.text = crypto?.symbol.toString().uppercase(Locale.ROOT)

        binding.cryptoName.text = crypto?.name

        binding.cryptoCurrentPrice.text = currencySymbol
            .plus(crypto?.currentPrice?.let { getFormattedPrice(it) }.toString())

        val isPositive = (crypto?.priceChangePercentage24h ?: 0.0) > 0.0
        val textColor =
            if (isPositive) {
                ContextCompat.getColor(binding.root.context, R.color.jungle_green)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.dark_terra_cotta)
            }
        binding.cryptoPriceChangePercentage.setTextColor(textColor)

        val symbolPlus = if (isPositive) "+" else ""

        binding.cryptoPriceChangePercentage.text =
            symbolPlus.plus(
                crypto?.priceChangePercentage24h?.let { roundToTwoZero(it) }.toString()
                    .plus("%")
            )

        binding.root.setOnClickListener {
            clickListener.onItemClick(crypto)
        }
    }

    private fun roundToTwoZero(number: Double): Double {
        return BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun getFormattedPrice(number: Double): String {
        val roundedNumber = roundToTwoZero(number).toBigDecimal()
        val stringRoundedNumber = roundedNumber.toString().split(".")
        val wholePart = stringRoundedNumber[0]
        val numWholeString = numberToString(wholePart.toInt())
        val stringWholePart = numWholeString.replace("\\s".toRegex(), ",")
        val decimalPart = stringRoundedNumber[1]

        return "$stringWholePart.$decimalPart"
    }

    fun numberToString(value: Int): String {
        return String.format(Locale("ru", "RU"), "%,d", value)
    }
}

