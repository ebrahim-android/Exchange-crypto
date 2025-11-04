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

// Dagger Hilt Module for providing network-related dependencies.
@Module
// Specifies that the provided dependencies live as long as the application process (Singleton scope).
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a configured OkHttpClient instance, including an HTTP cache.
     * @param ctx Application context provided by Hilt.
     */
    @Provides
    fun provideOkHttpClient(@ApplicationContext ctx: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val cacheSize = 10L * 1024 * 1024 // Define 10 MB cache size.
        // Set up the cache directory and size.
        builder.cache(Cache(File(ctx.cacheDir, "http_cache"), cacheSize))
        return builder.build()
    }

    /**
     * Provides the Retrofit instance configured with the base URL and Gson converter.
     * @param client The configured OkHttpClient instance (with caching).
     */
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Provides the CoinGeckoService implementation (used for the main markets list).
     */
    @Provides
    fun provideService(retrofit: Retrofit): CoinGeckoService =
        retrofit.create(CoinGeckoService::class.java)

    /**
     * Provides the CoinGeckoApi implementation (used for details and chart data).
     */
    @Provides
    fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi =
        retrofit.create(CoinGeckoApi::class.java)

    /**
     * Provides the implementation of the main list repository.
     * Maps the API service dependency to the domain contract (CryptoRepository).
     */
    @Provides
    fun provideRepo(service: CoinGeckoService): CryptoRepository =
        CryptoRepositoryImpl(service)

}