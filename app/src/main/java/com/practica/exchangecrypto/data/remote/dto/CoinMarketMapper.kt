package com.practica.exchangecrypto.data.remote.dto

import com.practica.exchangecrypto.domain.model.CryptoModel

fun CoinMarketDto.toDomain(): CryptoModel {
    return CryptoModel(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap,
        priceChange24h = priceChangePercentage24h
    )
}
