package com.practica.exchangecrypto.domain.model

// Data class representing the full, detailed information for a single cryptocurrency.
// This includes market stats and historical data for the chart.
data class CryptoDetail (
    val id: String,                 // Unique identifier.
    val name: String,               // Full name of the currency.
    val currentPrice: Double,       // Current price value.
    val marketCap: Double?,         // Total market capitalization (nullable).
    val volume24h: Double?,         // 24-hour trading volume (nullable).
    val high24h: Double?,           // Highest price recorded in the last 24 hours (nullable).
    val low24h: Double?,            // Lowest price recorded in the last 24 hours (nullable).
    val priceChange24h: Double?,    // 24-hour price change percentage (nullable).
    // List of historical price points used to generate the detailed price chart.
    val history: List<Double>
)