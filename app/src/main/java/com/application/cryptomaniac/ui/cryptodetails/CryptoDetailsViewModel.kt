package com.application.cryptomaniac.ui.cryptodetails

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.cryptomaniac.CryptoRepository
import com.application.cryptomaniac.NetworkState
import com.application.cryptomaniac.data.model.CryptoDescAndCategories
import kotlinx.coroutines.launch


class CryptoDetailsViewModel(val cryptoRepository: CryptoRepository?) : ViewModel() {
    val id = ObservableField<String>()
    val image = ObservableField<String>()

    private val _cryptoDescAndCategories = MutableLiveData<CryptoDescAndCategories?>()
    val cryptoDescAndCategories: LiveData<CryptoDescAndCategories?> =
        _cryptoDescAndCategories

    fun getChosenItemDescAndCategories() {
        viewModelScope.launch {
            cryptoRepository?.let {
                id.get()?.let {
                    when (val descAndCategories =
                        cryptoRepository.getDescAndCategories(it)) {
                        is NetworkState.Success -> {
                            _cryptoDescAndCategories.postValue(descAndCategories.data)
                        }
                        is NetworkState.Error -> {
                            Log.e("ERROR", "NetworkState error")
                        }
                    }
                }

            }
        }

    }

    companion object {
        fun factory(
            cryptoRepository: CryptoRepository?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CryptoDetailsViewModel(cryptoRepository) as T
            }
        }
    }

}