package com.practica.exchangecrypto.data.repository

import com.practica.exchangecrypto.data.remote.api.CoinGeckoApi
import com.practica.exchangecrypto.domain.model.CryptoDetail
import javax.inject.Inject

// Repository class responsible for fetching detailed data from the remote source (API).
class DetailRepository @Inject constructor(
    // Injects the CoinGecko API interface provided by Dagger Hilt.
    private val api: CoinGeckoApi
) {
    /**
     * Fetches comprehensive details and historical data for a specific cryptocurrency.
     *
     * @param id The ID of the cryptocurrency.
     * @param day The time range for historical chart data (defaults to 7 days).
     * @return A mapped CryptoDetail domain model.
     */
    suspend fun getCryptoDetail(id: String, day: String = "7"): CryptoDetail {
        // 1. Fetch main coin details (market stats, etc.).
        val detailResp = api.getCoinDetail(id)
        // 2. Fetch market chart data (price history) for the chart.
        val chartResp = api.getMarketChart(id, "usd", day)

        // Safely extract market data to avoid null checks later.
        val md = detailResp.marketData

        // --- Data Extraction and Null Handling ---
        // Safely extract price for 'usd', defaulting to 0.0 if null.
        val currentPrice = md?.currentPrice?.get("usd") ?: 0.0
        val marketCap = md?.marketCap?.get("usd")
        val volume = md?.totalVolume?.get("usd")
        val high = md?.high24h?.get("usd")
        val low = md?.low24h?.get("usd")
        val change = md?.priceChangePercentage24h

        // Map historical prices: extract the price value (index 1) from the nested list structure
        // provided by the API, filtering out any nulls.
        val history = chartResp.prices.mapNotNull { it.getOrNull(1) }

        // --- Map to Domain Model ---
        return CryptoDetail(
            id = detailResp.id,
            name = detailResp.name,
            currentPrice = currentPrice,
            marketCap = marketCap,
            volume24h = volume,
            high24h = high,
            low24h = low,
            priceChange24h = change,
            history = history
        )
    }
}