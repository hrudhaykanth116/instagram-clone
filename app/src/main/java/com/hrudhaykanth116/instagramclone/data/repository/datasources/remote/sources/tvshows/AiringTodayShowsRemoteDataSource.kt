package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows

import com.hrudhaykanth116.instagramclone.data.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.BaseRemoteDataSource
import javax.inject.Inject

class AiringTodayShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis
): BaseRemoteDataSource() {

    suspend fun getAiringTodayShows(pageId: Int): Resource<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getAiringTodayShows(pageId)
        }
    }

}