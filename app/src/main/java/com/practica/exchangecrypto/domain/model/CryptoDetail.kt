package com.practica.exchangecrypto.domain.model

data class CryptoDetail (
    val id: String,
    val name: String,
    val currentPrice: Double,
    val marketCap: Double?,
    val volume24h: Double?,
    val high24h: Double?,
    val low24h: Double?,
    val priceChange24h: Double?,
    val history: List<Double>
)