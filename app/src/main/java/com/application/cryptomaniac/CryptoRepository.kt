package com.application.cryptomaniac

import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.data.model.CryptoDescAndCategories

class CryptoRepository (val api: ApiService) {

    suspend fun getUsdOrEur(currency: String): NetworkState<List<Crypto>> {
        val response = api.getUsdOrEur(vs_currency = currency)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    suspend fun getDescAndCategories(id: String): NetworkState<List<CryptoDescAndCategories>> {
        val response = api.getDescAndCategories(id)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}