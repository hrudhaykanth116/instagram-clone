package com.hrudhaykanth116.instagramclone.di.modules

import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    public const val BASE_URL: String = "https://instagram-clone-db-116.firebaseio.com/"
    private const val BASE_URL: String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideRetroApis(retrofit: Retrofit): RetroApis =
        retrofit.create(RetroApis::class.java)

}