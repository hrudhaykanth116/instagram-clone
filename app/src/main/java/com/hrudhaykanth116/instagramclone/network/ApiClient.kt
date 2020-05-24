package com.hrudhaykanth116.instagramclone.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object {
        public const val BASE_URL: String = "https://instagram-clone-db-116.firebaseio.com/"
        private var retrofit: Retrofit? = null

        public fun getClient(): Retrofit? {
            if (retrofit == null) {

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

    }

}