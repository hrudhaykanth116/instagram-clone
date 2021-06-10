package com.hrudhaykanth116.instagramclone.data.repository.repositories

import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource
) {

    suspend fun getTvShowDetails(tvShowId: Int): Resource<TvShowDetails> {
        return tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
    }

}