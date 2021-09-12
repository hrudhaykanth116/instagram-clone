package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.instagramclone.data.models.GetTvReviewsResponse
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.TvApisService
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.random.Random

class TvShowReviewsRemoteDataSource constructor(
    private val tvShowId: Int,
    private val tvApisService: TvApisService
) : PagingSource<Int, GetTvReviewsResponse.ReviewDetails>() {

    private var initialPageId = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetTvReviewsResponse.ReviewDetails> {
        val currentKey = params.key ?: initialPageId

        Log.d(TAG, "load: currentKey: $currentKey")

        return try {
            val discoverTvShowsResponse: Response<GetTvReviewsResponse> =
                tvApisService.getTvReviews(tvShowId, currentKey)

            val tvShowsList: List<GetTvReviewsResponse.ReviewDetails>? =
                discoverTvShowsResponse.body()?.reviewDetails

            // TODO: 29/05/21 Check if the response is successful

            if (tvShowsList.isNullOrEmpty()) {
                Log.e(TAG, "load: Error: ${discoverTvShowsResponse.message()}")
                LoadResult.Error(
                    Exception("Error: ${discoverTvShowsResponse.message()}")
                )
            }else{
                LoadResult.Page(
                    data = tvShowsList,
                    prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                    nextKey = currentKey + 1
                )
            }
        } catch (exception: IOException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, GetTvReviewsResponse.ReviewDetails>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        /*val refreshKey = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        return refreshKey*/
        val refreshKey = Random.nextInt(1, 10)
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    companion object {
        private val TAG: String = TvShowReviewsRemoteDataSource::class.java.name
    }

}