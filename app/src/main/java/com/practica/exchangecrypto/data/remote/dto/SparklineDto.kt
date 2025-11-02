package com.practica.exchangecrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SparklineIn7dDto(
    @SerializedName("price")
    val price: List<Double>?
)
