package com.practica.exchangecrypto.ui.home

import com.practica.exchangecrypto.domain.model.CryptoModel
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

// Función de extensión: convierte CryptoModel en CryptoHorizontal
fun CryptoModel.toHorizontal(): CryptoHorizontal {
    return CryptoHorizontal(
        iconUrl = imageUrl,
        name = name,
        symbol = symbol,
        price = "$$currentPrice",
        change = "${priceChange24h}%",
        sparkline = sparkline // aquí va la lista real
    )
}
