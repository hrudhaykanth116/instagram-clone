package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit

import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApisService {

    @GET("tv/{tv_id}/images")
    suspend fun getTvShowImages(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<GetTvImagesResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<GetTvVideosResponse>

    @GET("tv/{tv_id}/similar")
    suspend fun getTvShowsSimilar(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<TvShowDataPagedResponse>

    @GET("tv/{tv_id}/similar")
    suspend fun getTvReviews(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<GetTvReviewsResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCredits(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY
    ): Response<GetTvCreditsResponse>

}