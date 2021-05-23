package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hrudhaykanth116.instagramclone.repository.models.NetworkState
import com.hrudhaykanth116.instagramclone.repository.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class TopRatedTvShowsDataSource @Inject constructor(
    private val retroApis: RetroApis
): PageKeyedDataSource<Int, TvShowData>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShowData>
    ) {
        Log.i(TAG, "loadInitial: ")
        networkState.postValue(NetworkState.LOADING)

        try {
            val popularTvShowsCall = retroApis.getTopRatedTvShows(1)
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
                        callback.onResult(tvShowDataPagedResponse.tvShowsList, params.key + 1)
                    }
                } else {
                    networkState.postValue(NetworkState.FAILED)
                }
            }

        }
        retroApis.getTopRatedTvShows(params.key).enqueue(callBack)

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShowData>
    ) {
    }

    companion object{
        private val TAG: String = TopRatedTvShowsDataSource::class.java.name
    }

}