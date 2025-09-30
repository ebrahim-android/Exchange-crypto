package com.practica.exchangecrypto.data.remote.api

import com.practica.exchangecrypto.data.remote.dto.CoinMarketDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoService {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vsCurrency: String,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") per_page: Int = 50,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") price_change_percentage: String? = null
    ): List<CoinMarketDto>
}