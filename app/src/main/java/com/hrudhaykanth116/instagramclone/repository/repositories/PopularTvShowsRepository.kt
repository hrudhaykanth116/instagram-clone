package com.hrudhaykanth116.instagramclone.repository.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.PopularTvShowsDataSource
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularTvShowsRepository @Inject constructor(
    private val retroApis: RetroApis,
) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getTvShows(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                PopularTvShowsDataSource(retroApis)
            }
        ).flow
    }

    companion object {

        private const val TAG = "PopularTvShowsRepositor"

        const val PAGE_SIZE = 10
    }

}