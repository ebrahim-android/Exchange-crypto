package com.practica.exchangecrypto.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.exchangecrypto.ui.model.CryptoItem

class SharedCryptoViewModel : ViewModel() {

    private val _cryptoList = MutableLiveData<List<CryptoItem>>()
    val cryptoList: LiveData<List<CryptoItem>> get() = _cryptoList

    fun setCryptoList(list: List<CryptoItem>) {
        _cryptoList.value = list
    }

}
