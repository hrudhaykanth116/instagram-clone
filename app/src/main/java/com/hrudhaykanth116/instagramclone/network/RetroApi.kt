package com.hrudhaykanth116.instagramclone.network

import com.hrudhaykanth116.instagramclone.models.UserPostsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


public interface RetroApi {

//    @GET("users/{user}/repos")
//    fun listRepos(@Path("user") user: String?): Call<List<UserPost>>?

//    @POST("/upload/{new}.json")
//    fun setData(
//        @Path("new") s1: String?,
//        @Body user: User?
//    ): Call<User?>?
//
//    @GET("/upload/sushil.json")
//    fun getData(): Call<User?>?
//
//    @PUT("/upload/{new}.json")
//    fun setDataWithoutRandomness(
//        @Path("new") s1: String?,
//        @Body user: User?
//    ): Call<User?>?


    @Headers("Content-Type: application/json")
    @GET("user_posts.json")
    public fun getUserPostsJson(): Call<UserPostsData>?

}