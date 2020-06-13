package com.hrudhaykanth116.instagramclone.network

import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.models.PopularTvShowsResponse
import com.hrudhaykanth116.instagramclone.models.UserPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


public interface RetroApis {

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
    @GET("user_posts/page{pageID}.json")
    public fun getUserPosts(@Path("pageID") pageId: Int): Call<List<UserPost>>?

    @GET("movie/popular")
    public fun getPopularMoviesList(@Query("page") pageId: Int,
                                    @Query("api_key", encoded = true) apiKey: String = MoviesDbConstants.API_KEY
    ): Call<PopularMoviesResponse>

    @GET("tv/popular/")
    public fun getPopularTvShows(@Query("page") pageId: Int,
                                 @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Call<PopularTvShowsResponse>

}