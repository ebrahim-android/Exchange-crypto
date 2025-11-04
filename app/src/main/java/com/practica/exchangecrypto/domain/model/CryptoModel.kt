package com.practica.exchangecrypto.domain.model

// Data class representing the core business model for a cryptocurrency.
// This model should be independent of UI structure or API format.
data class CryptoModel(
    val id: String,                 // Unique identifier of the crypto.
    val symbol: String,             // Trading symbol (e.g., BTC, ETH).
    val name: String,               // Full name of the currency.
    val imageUrl: String,           // Link to the cryptocurrency icon.
    val currentPrice: Double,       // Current price value.
    val marketCap: Long?,           // Total market capitalization (nullable as it might be missing).
    val priceChange24h: Double?,    // 24-hour price change percentage (nullable).
    val totalVolume: Double?,       // 24-hour total trading volume (nullable).
    // List of historical prices for a quick chart visualization (sparkline).
    val sparkline: List<Double> = emptyList()
)