package com.practica.exchangecrypto.ui.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.exchangecrypto.ui.model.CryptoItem

class SharedCryptoViewModel :
    ViewModel() { // SharedCryptoViewModel help us to connect the data between fragments,
    // in this case between HomeFragment and SearchFragment

    // ---- this part is for HomeFragment ----
    // ---- it show us the list of cryptocurrencies ----
    private val _cryptoList = MutableLiveData<List<CryptoItem>>()
    val cryptoList: LiveData<List<CryptoItem>> get() = _cryptoList

    // ---- this part is for SearchFragment ----
    // ---- it show us the selected crypto ----
    private val _selectedCrypto = MutableLiveData<CryptoItem?>()
    val selectedCrypto: LiveData<CryptoItem?> get() = _selectedCrypto

    // ---- this part is for FavoritesFragment ----
    // ---- it show us the list of favorite cryptocurrencies ----
    val _favoriteCryptos = MutableLiveData<List<CryptoItem>>(mutableListOf())
    val favoriteCryptos: LiveData<List<CryptoItem>> get() = _favoriteCryptos


    // --- Functions ---
    fun setCryptoList(list: List<CryptoItem>) { // We set the list of cryptocurrencies from HomeFragment
        _cryptoList.value = list
    }

    fun selectCrypto(crypto: CryptoItem?) { // We set the selected crypto from SearchFragment
        _selectedCrypto.value = crypto
    }

    fun toggleFavorite(crypto: CryptoItem) {
        val currentList = _favoriteCryptos.value?.toMutableList() ?: mutableListOf()
        if (currentList.any { it.id == crypto.id }) {
            currentList.removeAll { it.id == crypto.id }
            Log.d("FAVORITES", "Removed: ${crypto.name}")
        } else {
            currentList.add(crypto)
            Log.d("FAVORITES", "Added: ${crypto.name}")
        }
        _favoriteCryptos.value = currentList
    }

    fun isFavotire(crypto: CryptoItem): Boolean{ // We check if a crypto is favorite 
        return _favoriteCryptos.value?.any { it.id == crypto.id } == true
    }

}


