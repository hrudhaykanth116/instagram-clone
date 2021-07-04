package com.hrudhaykanth116.instagramclone.data.repository.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows.TopRatedTvShowsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedTvShowsRepository @Inject constructor(
    private val retroApis: RetroApis,
) {

    fun getTvShowsPagingData(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TopRatedTvShowsRemoteDataSource(retroApis)
            }
        ).flow
    }

    companion object {

        private const val TAG = "TopRatedTvShowsReposito"

        private const val PAGE_SIZE = 12
    }

}