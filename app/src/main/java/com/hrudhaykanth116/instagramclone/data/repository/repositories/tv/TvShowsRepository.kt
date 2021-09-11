package com.hrudhaykanth116.instagramclone.data.repository.repositories.tv

import com.hrudhaykanth116.instagramclone.data.models.*
import com.hrudhaykanth116.instagramclone.data.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.models.search.TvShowSearchResults
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource
) {

    suspend fun getTvShowDetails(tvShowId: Int): Resource<TvShowDetails> {
        return tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
    }

    suspend fun searchTvShow(query: String): Resource<TvShowSearchResults>{
        return tvShowsRemoteDataSource.searchTvShow(query)
    }

    suspend fun getTvGenres(): Resource<GetTvGenresResponse> {
        return tvShowsRemoteDataSource.getTvGenres()
    }

    suspend fun getTvImages(tvId: Int): Resource<GetTvImagesResponse> {
        return tvShowsRemoteDataSource.getTvImages(tvId)
    }

    suspend fun getTvShowVideos(tvId: Int): Resource<GetTvVideosResponse> {
        return tvShowsRemoteDataSource.getTvShowVideos(tvId)
    }

    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): Resource<TvShowDataPagedResponse> {
        return tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
    }

    suspend fun getTvReviews(tvId: Int, pageId: Int): Resource<GetTvReviewsResponse> {
        return tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
    }

    suspend fun getTvCredits(tvId: Int): Resource<GetTvCreditsResponse> {
        return tvShowsRemoteDataSource.getTvCredits(tvId)
    }

}