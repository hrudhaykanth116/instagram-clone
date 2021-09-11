package com.hrudhaykanth116.instagramclone.di.modules

import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.TvApisService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    public const val BASE_URL: String = "https://instagram-clone-db-116.firebaseio.com/"
    private const val BASE_URL: String = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideRetroApis(retrofit: Retrofit): RetroApis =
        retrofit.create(RetroApis::class.java)

    @Provides
    @Singleton
    fun provideTvApisService(retrofit: Retrofit): TvApisService =
        retrofit.create(TvApisService::class.java)

}