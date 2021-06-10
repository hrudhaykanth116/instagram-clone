package com.hrudhaykanth116.instagramclone.data.repository.repositories.tv

import com.hrudhaykanth116.instagramclone.data.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows.AiringTodayShowsRemoteDataSource
import javax.inject.Inject

class AiringTodayShowsRepository @Inject constructor(
    private val airingTodayShowsRemoteDataSource: AiringTodayShowsRemoteDataSource
) {

    suspend fun getAiringTodayShows(pageId: Int): Resource<TvShowDataPagedResponse> {
        return airingTodayShowsRemoteDataSource.getAiringTodayShows(pageId)
    }

}