package com.practica.exchangecrypto.data.remote.dto

import android.util.Log
import com.practica.exchangecrypto.domain.model.CryptoModel

// Define el DTO que estás usando (asumiendo que existe)
// data class CoinMarketDto(...)

fun CoinMarketDto.toDomain(): CryptoModel {
    // 1. Crea la variable para el sparkline y regístrala
    val sparklineData = sparklineIn7d?.price ?: emptyList()

    // 2. Coloca el Log ANTES de la sentencia 'return'
    Log.d("CoinMarketDto",
        "Mapping $name. Sparkline size: ${sparklineData.size}. Data (first 5): ${sparklineData.take(5)}"
    )

    // 3. Retorna el modelo de dominio
    val totalVolume = null
    return CryptoModel(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap,
        priceChange24h = priceChangePercentage24h,
        totalVolume = totalVolume,
        sparkline = sparklineData // Usa la variable registrada
    )
}