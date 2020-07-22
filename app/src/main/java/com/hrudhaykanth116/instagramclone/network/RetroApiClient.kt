package com.hrudhaykanth116.instagramclone.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroApiClient {

    companion object {
//        public const val BASE_URL: String = "https://instagram-clone-db-116.firebaseio.com/"
        private const val BASE_URL: String = "https://api.themoviedb.org/3/"
        private var retrofit: Retrofit? = null
        private var retroApis: RetroApis? = null

        public fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit as Retrofit
        }

        public fun getRetroApiService(): RetroApis {
            if (retroApis == null) {
                retroApis = getRetrofitInstance().create(RetroApis::class.java)
            }
            return retroApis as RetroApis
        }

    }

}