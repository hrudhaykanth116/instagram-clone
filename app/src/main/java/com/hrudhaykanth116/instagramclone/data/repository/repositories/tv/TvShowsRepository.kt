package com.hrudhaykanth116.instagramclone.data.repository.repositories.tv

import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
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

}