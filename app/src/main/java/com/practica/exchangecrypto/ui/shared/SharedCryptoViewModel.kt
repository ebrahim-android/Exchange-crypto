package com.practica.exchangecrypto.ui.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.exchangecrypto.ui.model.CryptoItem

// ViewModel scoped to the Activity, used to share data (communicate) between Fragments
// (e.g., Home, Search, Detail, and Favorites).
class SharedCryptoViewModel : ViewModel() {

    // --- Data for HomeFragment / SearchFragment (Full List) ---
    // MutableLiveData holding the comprehensive list of cryptocurrencies loaded initially.
    private val _cryptoList = MutableLiveData<List<CryptoItem>>()
    val cryptoList: LiveData<List<CryptoItem>> get() = _cryptoList

    // --- Data for DetailFragment (Selected Item) ---
    // MutableLiveData holding the currently selected crypto item (used for navigation to detail).
    private val _selectedCrypto = MutableLiveData<CryptoItem?>()
    val selectedCrypto: LiveData<CryptoItem?> get() = _selectedCrypto

    // --- Data for FavoritesFragment (Favorites List) ---
    // MutableLiveData holding the list of cryptocurrencies marked as favorites.
    val _favoriteCryptos = MutableLiveData<List<CryptoItem>>(mutableListOf())
    val favoriteCryptos: LiveData<List<CryptoItem>> get() = _favoriteCryptos


    // --- Functions ---
    /**
     * Sets the complete list of cryptocurrencies, typically called by HomeFragment.
     */
    fun setCryptoList(list: List<CryptoItem>) {
        _cryptoList.value = list
    }

    /**
     * Sets the selected cryptocurrency item, used before navigating to the DetailFragment.
     */
    fun selectCrypto(crypto: CryptoItem?) {
        _selectedCrypto.value = crypto
    }

    /**
     * Adds or removes a cryptocurrency from the favorites list.
     */
    fun toggleFavorite(crypto: CryptoItem) {
        // Get the current list and make it mutable for modification.
        val currentList = _favoriteCryptos.value?.toMutableList() ?: mutableListOf()

        // Check if the item already exists using its unique ID.
        if (currentList.any { it.id == crypto.id }) {
            // Item is a favorite: remove it.
            currentList.removeAll { it.id == crypto.id }
            Log.d("FAVORITES", "Removed: ${crypto.name}")
        } else {
            // Item is not a favorite: add it.
            currentList.add(crypto)
            Log.d("FAVORITES", "Added: ${crypto.name}")
        }
        // Update the LiveData, triggering observers in the Favorites UI.
        _favoriteCryptos.value = currentList
    }

    /**
     * Checks if a given cryptocurrency is currently in the favorites list.
     */
    fun isFavotire(crypto: CryptoItem): Boolean {
        // Use 'any' to efficiently check if an item with the same ID exists.
        return _favoriteCryptos.value?.any { it.id == crypto.id } == true
    }

}