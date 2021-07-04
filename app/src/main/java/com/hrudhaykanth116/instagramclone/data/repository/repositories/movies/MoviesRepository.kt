package com.hrudhaykanth116.instagramclone.data.repository.repositories.movies

import com.hrudhaykanth116.instagramclone.data.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.movies.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) {

    suspend fun getPopularMoviesList(pageId: Int): Resource<PopularMoviesResponse> {
        return moviesRemoteDataSource.getPopularMoviesList(pageId)
    }

}