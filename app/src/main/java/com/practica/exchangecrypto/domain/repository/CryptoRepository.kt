package com.practica.exchangecrypto.domain.repository

import com.practica.exchangecrypto.domain.model.CryptoModel
import javax.inject.Inject

//the interface will get the data
interface CryptoRepository {
    suspend fun getMarkets( //suspend cuz it will work with coroutines
        vsCurrency:String = "USD",
        page: Int = 1,
        perPage: Int = 50
    ):List<CryptoModel>
}

