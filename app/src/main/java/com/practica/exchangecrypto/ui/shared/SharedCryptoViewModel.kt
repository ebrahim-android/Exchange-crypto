package com.practica.exchangecrypto.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.exchangecrypto.ui.model.CryptoItem

class SharedCryptoViewModel : ViewModel() { // SharedCryptoViewModel help us to connect the data between fragments,
    // in this case between HomeFragment and SearchFragment

    // ---- this part is for HomeFragment ----
    // ---- it show us the list of cryptocurrencies ----
    private val _cryptoList = MutableLiveData<List<CryptoItem>>()
    val cryptoList: LiveData<List<CryptoItem>> get() = _cryptoList

    // ---- this part is for SearchFragment ----
    // ---- it show us the selected crypto ----
    private val _selectedCrypto = MutableLiveData<CryptoItem?>()
    val selectedCrypto: LiveData<CryptoItem?> get() = _selectedCrypto

    fun setCryptoList(list: List<CryptoItem>) { // We set the list of cryptocurrencies from HomeFragment
        _cryptoList.value = list
    }

    fun selectCrypto(crypto: CryptoItem?) { // We set the selected crypto from SearchFragment
        _selectedCrypto.value = crypto
    }

}
