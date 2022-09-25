package com.application.cryptomaniac.ui.cryptodetails

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.cryptomaniac.CryptoRepository
import com.application.cryptomaniac.network.NetworkState
import com.application.cryptomaniac.data.model.CryptoDescAndCategories
import kotlinx.coroutines.launch

class CryptoDetailsViewModel(val cryptoRepository: CryptoRepository?) : ViewModel() {
    val id = ObservableField<String>()

    private val _cryptoDescAndCategories = MutableLiveData<CryptoDescAndCategories?>()
    val cryptoDescAndCategories: LiveData<CryptoDescAndCategories?> =
        _cryptoDescAndCategories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getChosenItemDescAndCategories() {
        viewModelScope.launch {
            cryptoRepository?.let {
                _isLoading.value = true

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
                _isLoading.value = false

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