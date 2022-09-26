package com.application.cryptomaniac.ui.cryptomain

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.application.cryptomaniac.CryptoRepository
import com.application.cryptomaniac.data.model.Crypto
import com.application.cryptomaniac.network.NetworkState
import com.application.cryptomaniac.utils.Currency.EUR
import com.application.cryptomaniac.utils.Currency.USD
import kotlinx.coroutines.launch
import java.util.*

class CryptoMainViewModel(val cryptoRepository: CryptoRepository?) : ViewModel() {

    private val _cryptoList = MutableLiveData<List<Crypto?>?>()
    val cryptoList: LiveData<List<Crypto?>?> = _cryptoList
    val isUsd = ObservableBoolean(true)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCurrentList() {
        val type = if (isUsd.get()) USD.name else EUR.name

        viewModelScope.launch {
            cryptoRepository?.let {
                _isLoading.value = true

                cryptoRepository
                    .getUsdOrEur(type.lowercase(Locale.getDefault()))
                when (val listUsd =
                    cryptoRepository.getUsdOrEur(type.lowercase(Locale.getDefault()))) {
                    is NetworkState.Success -> {
                        _cryptoList.postValue(listUsd.data)
                        Log.i("sasha", "getCurrentList: ${listUsd.data}")
                    }
                    is NetworkState.Error -> {
                        Log.e("ERROR", "NetworkState error")
                    }
                }
                _isLoading.value = false
            }
        }
    }

    companion object {
        fun factory(
            cryptoRepository: CryptoRepository?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CryptoMainViewModel(cryptoRepository) as T
            }
        }
    }

}