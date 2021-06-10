package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.movies

import com.hrudhaykanth116.instagramclone.data.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.BaseRemoteDataSource
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    val retroApis: RetroApis
): BaseRemoteDataSource() {

    suspend fun getPopularMoviesList(pageId: Int): Resource<PopularMoviesResponse> {
        return getResult {
            retroApis.getPopularMoviesList(pageId)
        }

    }

}