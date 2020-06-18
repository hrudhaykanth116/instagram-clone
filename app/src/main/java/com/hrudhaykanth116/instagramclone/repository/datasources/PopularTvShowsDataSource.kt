package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.PopularTvShowsResponse
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import com.hrudhaykanth116.instagramclone.network.RetroApis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException

/**
 * Data source which is used to load data initially and when new data is required.
 */
class PopularTvShowsDataSource: PageKeyedDataSource<Int, TvShowData>() {

    private val TAG: String = PopularTvShowsDataSource::class.java.name

    private var retroApiClient: Retrofit = RetroApiClient.getRetrofitInstance()
    private var retroApis: RetroApis
    val networkState = MutableLiveData<NetworkState>()

    init {
        retroApis = retroApiClient.create(RetroApis::class.java)
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShowData>
    ) {
        Log.i(TAG, "loadInitial: ")
        networkState.postValue(NetworkState.LOADING)

        try {
            val popularTvShowsCall = retroApis.getPopularTvShows(1)
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

        val callBack = object : Callback<PopularTvShowsResponse> {
            override fun onFailure(call: Call<PopularTvShowsResponse>, t: Throwable) {
                networkState.postValue(NetworkState.FAILED)
            }

            override fun onResponse(
                call: Call<PopularTvShowsResponse>,
                response: Response<PopularTvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.LOADED)
                    val popularTvShowsResponse: PopularTvShowsResponse? = response.body()
                    popularTvShowsResponse?.let {
                        callback.onResult(popularTvShowsResponse.tvShowsList, params.key + 1)
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
    }

}