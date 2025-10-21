package com.practica.exchangecrypto.data.remote.api

import com.practica.exchangecrypto.data.remote.dto.CoinDetailResponse
import com.practica.exchangecrypto.data.remote.dto.MarketChartResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("sparkline") sparkline: Boolean = false
    ): CoinDetailResponse

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("days") days: String
    ): MarketChartResponse
}