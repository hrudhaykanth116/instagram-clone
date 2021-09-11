package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows

import com.hrudhaykanth116.instagramclone.data.models.*
import com.hrudhaykanth116.instagramclone.data.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.models.search.TvShowSearchResults
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.TvApisService
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.BaseRemoteDataSource
import javax.inject.Inject

class TvShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis,
    private val tvApisService: TvApisService
): BaseRemoteDataSource() {

    suspend fun fetchTvShowDetails(tvShowId: Int): Resource<TvShowDetails> {
        return getResult {
            retroApis.getTvShowDetails(tvShowId)
        }
    }

    suspend fun searchTvShow(query: String): Resource<TvShowSearchResults> {
        return getResult {
            retroApis.searchTv(query)
        }
    }

    suspend fun getTvGenres(): Resource<GetTvGenresResponse> {
        return getResult {
            retroApis.getTvGenres()
        }
    }

    suspend fun getTvImages(tvId: Int): Resource<GetTvImagesResponse>{
        return getResult {
            tvApisService.getTvShowImages(tvId)
        }
    }

    suspend fun getTvShowVideos(tvId: Int): Resource<GetTvVideosResponse> {
        return getResult {
            tvApisService.getTvShowVideos(tvId)
        }
    }

    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): Resource<TvShowDataPagedResponse> {
        return getResult {
            tvApisService.getTvShowsSimilar(tvId, pageId)
        }
    }

    suspend fun getTvReviews(tvId: Int, pageId: Int): Resource<GetTvReviewsResponse> {
        return getResult {
            tvApisService.getTvReviews(tvId, pageId)
        }
    }

    suspend fun getTvCredits(tvId: Int): Resource<GetTvCreditsResponse> {
        return getResult {
            tvApisService.getTvCredits(tvId)
        }
    }

}