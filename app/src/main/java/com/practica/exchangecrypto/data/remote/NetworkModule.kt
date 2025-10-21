package com.practica.exchangecrypto.data.remote


import android.content.Context
import com.practica.exchangecrypto.data.remote.api.CoinGeckoApi
import com.practica.exchangecrypto.data.remote.api.CoinGeckoService
import com.practica.exchangecrypto.data.repository.CryptoRepositoryImpl
import com.practica.exchangecrypto.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext ctx: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val cacheSize = 10L * 1024 * 1024 // 10 MB
        builder.cache(Cache(File(ctx.cacheDir, "http_cache"), cacheSize))
        return builder.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides // principal service (list of cryptos)
    fun provideService(retrofit: Retrofit): CoinGeckoService =
        retrofit.create(CoinGeckoService::class.java)

    @Provides // new service for details and graphics
    fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi =
        retrofit.create(CoinGeckoApi::class.java)

    @Provides // main repository (list)
    fun provideRepo(service: CoinGeckoService): CryptoRepository =
        CryptoRepositoryImpl(service)

}
