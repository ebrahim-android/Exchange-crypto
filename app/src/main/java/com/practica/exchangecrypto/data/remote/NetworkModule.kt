package com.practica.exchangecrypto.data.remote

import android.content.Context
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

@Module // provide dependencies
@InstallIn(SingletonComponent::class) // to limit of the instances
object NetworkModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext ctx: Context): OkHttpClient{
        val builder = OkHttpClient.Builder()

        val cacheSize = 10L * 1024 * 1024 //10 MB
        builder.cache(Cache(File(ctx.cacheDir, "http_cache"), cacheSize)) // It will save 10 MB of cache
                                                                               //to avoid downloading more information, use less data, it's faster, etc.
        return builder.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideService(retrofit: Retrofit): CoinGeckoService =
        retrofit.create(CoinGeckoService::class.java)

    @Provides
    fun provideRepo(service: CoinGeckoService): CryptoRepository =
        CryptoRepositoryImpl(service)
}


//
//    @Provides
//    fun provideMoshi(): Moshi = Moshi.Builder().build()
//
//    @Provides
//    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://api.coingecko.com/api/v3/")
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//
//    @Provides
//    fun provideService(retrofit: Retrofit): CoinGeckoService =
//        retrofit.create(CoinGeckoService::class.java)
//
//    @Provides
//    fun provideRepo(service: CoinGeckoService): CryptoRepository =
//        CryptoRepositoryImpl(service)
//}
