package com.hrudhaykanth116.instagramclone.data.repository.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.instagramclone.data.models.GetTvReviewsResponse
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.TvApisService
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows.TvShowReviewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowReviewsRepository @Inject constructor(
    private val tvApisService: TvApisService
) {

    fun getTvShowsPagingData(tvShowId: Int): Flow<PagingData<GetTvReviewsResponse.ReviewDetails>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowReviewsRemoteDataSource(tvShowId, tvApisService)
            }
        ).flow
    }

    companion object {

        private const val TAG = "TvShowSimilarShowsRepos"

        private const val PAGE_SIZE = 12
    }

}