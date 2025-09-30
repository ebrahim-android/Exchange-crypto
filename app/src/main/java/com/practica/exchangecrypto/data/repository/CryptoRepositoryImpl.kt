package com.practica.exchangecrypto.data.repository

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
            sparkline = false, //to graphic
            price_change_percentage = "24h" //We ask that you return the price change within 24 hours.
        )
        return dtos.map { it.toDomain() }
    }
}

