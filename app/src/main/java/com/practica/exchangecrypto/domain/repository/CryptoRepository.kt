package com.practica.exchangecrypto.domain.repository

import com.practica.exchangecrypto.domain.model.CryptoModel

// This interface defines the contract for fetching cryptocurrency data.
// It resides in the domain layer, ensuring the business logic remains independent of data sources.
interface CryptoRepository {

    /**
     * Fetches a paginated list of cryptocurrency markets.
     *
     * @param vsCurrency The reference currency (defaults to USD).
     * @param page The page number for pagination.
     * @param perPage The number of items per page.
     * @return A list of CryptoModel objects.
     */
    suspend fun getMarkets( // Suspend function because network operations are inherently asynchronous (coroutine required).
        vsCurrency:String = "USD",
        page: Int = 1,
        perPage: Int = 50,
    ):List<CryptoModel>
}