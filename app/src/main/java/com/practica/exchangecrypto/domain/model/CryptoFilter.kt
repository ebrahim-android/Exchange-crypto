package com.practica.exchangecrypto.domain.model

// Enum class defining the available filtering options for the cryptocurrency list.
enum class CryptoFilter(val displayName: String) {
    // Filter for cryptocurrencies with the highest positive price change in the last 24 hours.
    TOP_GAINERS("Top gainers"),

    // Filter for cryptocurrencies with the largest negative price change in the last 24 hours.
    TOP_LOSERS("Top losers"),

    // Filter based on the total 24-hour trading volume.
    VOLUME("Volume"),

    // Filter based on total market capitalization.
    MARKET_CAP("Market Cap"),

    // Default filter showing all available cryptos, usually sorted by Market Cap.
    ALL("All")
}