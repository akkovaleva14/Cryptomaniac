package com.application.cryptomaniac.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
    val id: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val image: String? = null,
    @SerialName("current_price")
    val currentPrice: Double = 0.0,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
)
