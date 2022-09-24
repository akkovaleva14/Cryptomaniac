package com.application.cryptomaniac.data.model

// import coingecko.internal.NullValueOmittingListSerializer
import coingecko.models.shared.Image
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDescAndCat(
    val id: String? = null,
    val name: String? = null,
    val categories: List<String>? = emptyList(),
    val description: Map<String, String>? = emptyMap(),
    val image: Image? = null,
)


/*  @Serializable(NullValueOmittingListSerializer::class)
    val categories: List<String> = emptyList(),
    val description: Map<String, String> = emptyMap(),
    val image: Image, */