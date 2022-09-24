package com.application.cryptomaniac.data.model

import coingecko.models.shared.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDescAndCategories(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("categories")
    val categories: List<String>? = emptyList(),

    @SerialName("description")
    val description: Map<String, String>? = emptyMap(),

    @SerialName("image")
    val image: Image? = null,
)