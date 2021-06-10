package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit

import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.*
import retrofit2.Call
import retrofit2.Response
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
    suspend fun getUserPosts(@Path("pageID") pageId: Int): Call<List<UserPost>>?

    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("page") pageId: Int,
        @Query("api_key", encoded = true) apiKey: String = MoviesDbConstants.API_KEY
    ): Response<PopularMoviesResponse>

    @GET("tv/popular/")
    suspend fun getPopularTvShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): TvShowDataPagedResponse

    @GET("tv/top_rated/")
    suspend fun getTopRatedTvShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): TvShowDataPagedResponse

    @GET("tv/airing_today/")
    suspend fun getAiringTodayShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<TvShowDataPagedResponse>

    @GET("tv/{tvShowId}")
    suspend fun getTvShowDetails(
        @Path("tvShowId") tvShowId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<TvShowDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Call<MovieVideosResponse>


}