package com.practica.exchangecrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

// Data Transfer Object (DTO) used by Retrofit/Gson to parse the nested sparkline price history
// section from the CoinGecko API response.
data class SparklineIn7dDto(
    // Maps the JSON field "price" to a List of Double values (historical price points).
    @SerializedName("price")
    val price: List<Double>?
)