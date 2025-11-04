package com.practica.exchangecrypto.data.remote.dto

// Data Transfer Object (DTO) for the response when requesting historical price data for a chart.
data class MarketChartResponse (
    // 'prices' is typically a list of lists, where each inner list contains:
    // [timestamp (Double), price_value (Double)].
    val prices: List<List<Double>>
)