package com.practica.exchangecrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

// Data Transfer Object (DTO) for the main CoinGecko coin detail endpoint response.
data class CoinDetailResponse (
    val id: String,
    val name: String,
    // Maps the nested JSON object "market_data" to the custom 'marketData' DTO. Nullable.
    @SerializedName("market_data") val marketData: marketData?
)

// DTO representing the nested 'market_data' section within the CoinDetailResponse.
data class marketData(
    // Maps price values for different currencies (e.g., {"usd": 50000.0}). Nullable.
    @SerializedName("current_price") val currentPrice: Map<String, Double>?,

    // Maps market cap values for different currencies. Nullable.
    @SerializedName("market_cap") val marketCap: Map<String, Double>?,

    // Maps total volume values for different currencies. Nullable.
    @SerializedName("total_volume") val totalVolume: Map<String, Double>?,

    // Maps 24h high price values for different currencies. Nullable.
    @SerializedName("high_24h") val high24h: Map<String, Double>?,

    // Maps 24h low price values for different currencies. Nullable.
    @SerializedName("low_24h") val low24h: Map<String, Double>?,

    // Price change percentage over the last 24 hours. Nullable.
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?
)