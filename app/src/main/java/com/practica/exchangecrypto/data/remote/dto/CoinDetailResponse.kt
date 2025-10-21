package com.practica.exchangecrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDetailResponse (
    val id: String,
    val name: String,
    @SerializedName("market_data") val marketData: marketData?
)

data class marketData(
    @SerializedName("current_price") val currentPrice: Map<String, Double>?,
    @SerializedName("market_cap") val marketCap: Map<String, Double>?,
    @SerializedName("total_volume") val totalVolume: Map<String, Double>?,
    @SerializedName("high_24h") val high24h: Map<String, Double>?,
    @SerializedName("low_24h") val low24h: Map<String, Double>?,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?
)