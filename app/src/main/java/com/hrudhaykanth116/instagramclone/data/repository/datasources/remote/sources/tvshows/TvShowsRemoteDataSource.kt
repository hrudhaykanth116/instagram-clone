package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows

import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.BaseRemoteDataSource
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import javax.inject.Inject

class TvShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis
): BaseRemoteDataSource() {

    suspend fun fetchTvShowDetails(tvShowId: Int): Resource<TvShowDetails> {
        return getResult {
            retroApis.getTvShowDetails(tvShowId)
        }
    }

}