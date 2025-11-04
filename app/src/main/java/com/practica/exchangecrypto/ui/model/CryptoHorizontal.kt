package com.practica.exchangecrypto.ui.model

// Data class representing an item displayed in the horizontal 'Top Cryptos' list (UI Model).
data class CryptoHorizontal(
    val name: String,
    val symbol: String,
    val price: String,                  // Formatted price string (e.g., "$123.45").
    val change: String,                 // Formatted 24h change string (e.g., "1.23%").
    val iconUrl: String,                // URL link for the crypto's icon/logo.
    // List of price points (Doubles) used to draw the small historical chart (sparkline).
    val sparkline: List<Double> = emptyList()
)