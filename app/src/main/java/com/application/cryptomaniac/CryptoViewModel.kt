package com.application.cryptomaniac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.cryptomaniac.Api.Companion.api
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {


    fun getCurrentList() {
        viewModelScope.launch {
            val listUsd: List<String>? = api?.getUsdOrEur("usd")
            val listEur: List<String>? = api?.getUsdOrEur("eur")


        }
    }

}