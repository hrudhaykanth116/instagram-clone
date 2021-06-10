package com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.sources.tvshows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.data.repository.datasources.remote.retrofit.RetroApis
import retrofit2.HttpException
import java.io.IOException
import kotlin.random.Random

/**
 * Data source which is used to load data initially and when new data is required.
 */
class PopularTvShowsRemoteDataSource constructor(
    private val retroApis: RetroApis,
): PagingSource<Int, TvShowData>() {

    private var initialPageId = Random.nextInt(1, 20)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val currentKey = params.key ?: initialPageId

        Log.d(TAG, "load: currentKey: $currentKey")

        return try {
            val tvShowDataPagedResponse: TvShowDataPagedResponse =
                retroApis.getPopularTvShows(currentKey)
            val tvShowsList = tvShowDataPagedResponse.tvShowsList

            // TODO: 29/05/21 Check if the response is successful
//            delay(5000)

            LoadResult.Page(
                data = tvShowsList,
                prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                nextKey = currentKey + 1
            )

        }catch (exception: IOException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
//        val refreshKey = state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
        val refreshKey = Random.nextInt(1, 20)
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    /*override fun invalidate() {
        super.invalidate()
        initialPageId = Random.nextInt(1, 20)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShowData>
    ) {
        Log.i(TAG, "loadInitial: ")
        networkState.postValue(NetworkState.LOADING)

        try {

            val popularTvShowsCall = retroApis.getPopularTvShows(initialPageId)
            val response = popularTvShowsCall.execute()
            if (response.isSuccessful) {
                networkState.postValue(NetworkState.LOADED)
                val popularMoviesResponse = response.body()
                popularMoviesResponse?.let {
                    val tvShowsList = popularMoviesResponse.tvShowsList
                    callback.onResult(tvShowsList, null, 2)
                }
            }else{
                handleFailure(response.message())
            }
        }catch (exception: UnknownHostException){
            handleFailure(exception.message)
            Log.e(TAG, "loadInitial: ", exception)
        }catch (exception: Exception){
            handleFailure(exception.message)
        }

    }

    private fun handleFailure(error: String?) {
        Log.e(TAG, "handleFailure: $error")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShowData>
    ) {
        Log.i(TAG, "loadAfter: ")
        networkState.postValue(NetworkState.LOADING)

        val callBack = object : Callback<TvShowDataPagedResponse> {
            override fun onFailure(call: Call<TvShowDataPagedResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

            override fun onResponse(
                call: Call<TvShowDataPagedResponse>,
                response: Response<TvShowDataPagedResponse>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.LOADED)
                    val tvShowDataPagedResponse: TvShowDataPagedResponse? = response.body()
                    tvShowDataPagedResponse?.let {
                        val nextPageId = params.key + 1
                        callback.onResult(tvShowDataPagedResponse.tvShowsList, nextPageId)
                    }
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

        }
        retroApis.getPopularTvShows(params.key).enqueue(callBack)

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShowData>
    ) {
    }*/

    companion object{
        private const val TAG = "PopularTvShowsDataSourc"
    }

}