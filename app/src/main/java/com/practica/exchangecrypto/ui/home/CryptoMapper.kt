package com.practica.exchangecrypto.ui.home

import com.practica.exchangecrypto.domain.model.CryptoModel
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

// Extension function to map the domain model (CryptoModel) to the UI presentation model (CryptoHorizontal).
fun CryptoModel.toHorizontal(): CryptoHorizontal {
    return CryptoHorizontal(
        iconUrl = imageUrl,
        name = name,
        symbol = symbol,
        // Format the current price by prepending a dollar sign.
        price = "$$currentPrice",
        // Format the 24-hour price change by appending a percentage sign.
        change = "${priceChange24h}%",
        // Map the sparkline historical data for the chart.
        sparkline = sparkline
    )
}