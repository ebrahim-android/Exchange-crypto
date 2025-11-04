package com.practica.exchangecrypto.data.remote.api

import com.practica.exchangecrypto.data.remote.dto.CoinDetailResponse
import com.practica.exchangecrypto.data.remote.dto.MarketChartResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit interface defining the API endpoints for fetching detailed coin information and charts.
interface CoinGeckoApi {

    /**
     * Fetches comprehensive details for a single cryptocurrency.
     * The coin's unique ID is passed as a path parameter.
     */
    @GET("coins/{id}")
    suspend fun getCoinDetail(
        // Path parameter: the ID of the coin (e.g., "bitcoin").
        @Path("id") id: String,
        // Query parameters are set to selectively include only essential data (market data).
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true, // We only need market data stats.
        @Query("sparkline") sparkline: Boolean = false
    ): CoinDetailResponse

    /**
     * Fetches historical price data needed to draw a chart.
     */
    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") id: String,
        // The reference currency for the prices (defaults to USD).
        @Query("vs_currency") vsCurrency: String = "usd",
        // The time range requested (e.g., "1", "7", "365"). This is mandatory.
        @Query("days") days: String
    ): MarketChartResponse
}