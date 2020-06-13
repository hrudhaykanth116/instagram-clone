package com.hrudhaykanth116.instagramclone.repository.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.network.RetroApi
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException

class PopularMoviesDataSource: PageKeyedDataSource<Int, MovieData>() {

    private val TAG: String = PopularMoviesDataSource::class.java.name

    private var retroApiClient: Retrofit = RetroApiClient.getRetrofitInstance()
    private var retrofitApiClient: RetroApi
    val networkState = MutableLiveData<NetworkState>()

    init {
        retrofitApiClient = retroApiClient.create(RetroApi::class.java)
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieData>
    ) {
        Log.i(TAG, "loadInitial: ")
        networkState.postValue(NetworkState.LOADING)

        try {
            val popularMoviesListCall = retrofitApiClient.getPopularMoviesList(1, MoviesDbConstants.API_KEY)
            val response = popularMoviesListCall.execute()
            val popularMoviesResponse = response.body()
            popularMoviesResponse?.let {
                val movieDataList = popularMoviesResponse.movieData
                callback.onResult(movieDataList, null, 2)
                networkState.postValue(NetworkState.LOADED)
            }
        }catch (exception: UnknownHostException){
            networkState.postValue(NetworkState.FAILED)
            Log.e(TAG, "loadInitial: ", exception)
        }catch (exception: Exception){
            Log.e(TAG, "loadInitial: ", exception)
        }

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieData>
    ) {
        Log.i(TAG, "loadAfter: ")
        networkState.postValue(NetworkState.LOADING)

        retrofitApiClient.getPopularMoviesList(params.key, MoviesDbConstants.API_KEY).enqueue(
            object : Callback<PopularMoviesResponse>{
                override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                    networkState.postValue(NetworkState.FAILED)
                }

                override fun onResponse(
                    call: Call<PopularMoviesResponse>,
                    response: Response<PopularMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        networkState.postValue(NetworkState.LOADED)
                        val body: PopularMoviesResponse? = response.body()
                        body?.let {
                            val movieDataList = body.movieData
                            callback.onResult(movieDataList, params.key + 1)
                        }
                    }else{
                        networkState.postValue(NetworkState.FAILED)
                    }
                }

            }
        )

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieData>
    ) {
    }

}