package com.practica.exchangecrypto.data.repository

import android.util.Log
import com.practica.exchangecrypto.data.remote.api.CoinGeckoService
import com.practica.exchangecrypto.data.remote.dto.toDomain
import com.practica.exchangecrypto.domain.model.CryptoModel
import com.practica.exchangecrypto.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CoinGeckoService
): CryptoRepository {

    override suspend fun getMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int
    ): List<CryptoModel> {
        val dtos = api.getCoinMarkets(
            vsCurrency = vsCurrency,
            order = "market_cap_desc", //sort by capitalization descending
            per_page = perPage, //it dosen't matter
            page = page, //it dosen't matter
            sparkline = true, //to graphic
            total_volume = "24h", //We ask that you return the total volume within 24 hours.
            price_change_percentage = "24h" //We ask that you return the price change within 24 hours.
        )
        Log.d("API_RAW", "First 5 coins sparkline sizes: ${dtos.take(5).map { it.sparklineIn7d?.price?.size }}")
        return dtos.map { it.toDomain() }
    }
}

