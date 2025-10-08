package com.practica.exchangecrypto.ui.model

data class CryptoItem(
    val name: String,
    val symbol: String,
    val price: String,
    val change: String,
    val iconUrl: String //Here we're gonna save the link from API
)
