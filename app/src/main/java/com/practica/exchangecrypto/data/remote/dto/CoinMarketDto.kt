package com.practica.exchangecrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

// Data Transfer Object (DTO) for the list of cryptocurrency markets fetched from the API.
// This model is used by Retrofit/Gson for deserialization.
data class CoinMarketDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,

    // Map JSON field 'current_price' to the Kotlin variable 'currentPrice'. Nullable.
    @SerializedName("current_price") val currentPrice: Double?,

    // Map JSON field 'market_cap' to the Kotlin variable 'marketCap'. Nullable.
    @SerializedName("market_cap") val marketCap: Long?,

    // Map JSON field 'price_change_percentage_24h' to the Kotlin variable 'priceChangePercentage24h'. Nullable.
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?,

    // Map JSON field 'sparkline_in_7d' to the nested SparklineIn7dDto object. Nullable.
    @SerializedName("sparkline_in_7d") val sparklineIn7d: SparklineIn7dDto?
)