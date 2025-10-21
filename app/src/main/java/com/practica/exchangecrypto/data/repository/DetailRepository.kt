package com.practica.exchangecrypto.data.repository

import com.practica.exchangecrypto.data.remote.api.CoinGeckoApi
import com.practica.exchangecrypto.domain.model.CryptoDetail
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: CoinGeckoApi
) {
    suspend fun getCryptoDetail(id: String, day: String = "7"): CryptoDetail {
        val detailResp = api.getCoinDetail(id)
        val chartResp = api.getMarketChart(id, "usd", day)

        val md = detailResp.marketData
        val currentPrice = md?.currentPrice?.get("usd") ?: 0.0
        val marketCap = md?.marketCap?.get("usd")
        val volume = md?.totalVolume?.get("usd")
        val high = md?.high24h?.get("usd")
        val low = md?.low24h?.get("usd")
        val change = md?.priceChangePercentage24h

        val history = chartResp.prices.mapNotNull { it.getOrNull(1) }

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
