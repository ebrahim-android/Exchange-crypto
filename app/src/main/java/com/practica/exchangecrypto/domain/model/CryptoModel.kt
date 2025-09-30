package com.practica.exchangecrypto.domain.model

//here can be the model "real" that from of the API 0r
//It represents our business logic here.
data class CryptoModel(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val marketCap: Long?,
    val priceChange24h: Double?
)