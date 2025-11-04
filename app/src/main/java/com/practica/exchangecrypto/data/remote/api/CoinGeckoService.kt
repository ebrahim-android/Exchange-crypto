package com.practica.exchangecrypto.data.remote.api

import com.practica.exchangecrypto.data.remote.dto.CoinMarketDto
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface defining the API endpoints for fetching coin market data.
interface CoinGeckoService {

    /**
     * Fetches a paginated list of coin markets with various sorting and filtering options.
     * Maps the JSON response list to a list of CoinMarketDto objects.
     */
    @GET("coins/markets")
    suspend fun getCoinMarkets(
        // Query parameter for the currency reference (e.g., "usd").
        @Query("vs_currency") vsCurrency: String,
        // Query parameter for sorting order (default: market cap descending).
        @Query("order") order: String = "market_cap_desc",
        // Query parameter for items per page (default: 50).
        @Query("per_page") per_page: Int = 50,
        // Query parameter for pagination page number (default: 1).
        @Query("page") page: Int = 1,
        // Query parameter to include sparkline historical data (default: true).
        @Query("sparkline") sparkline: Boolean = true,
        // Optional parameter for price change percentage interval.
        @Query("price_change_percentage") price_change_percentage: String? = null,
        // Optional parameter for total volume (not standard CoinGecko, possibly custom filtering).
        @Query("total_volume") total_volume: String? = null
    ): List<CoinMarketDto>
}