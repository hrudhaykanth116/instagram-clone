package com.hrudhaykanth116.instagramclone.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.repository.models.MovieData
import com.hrudhaykanth116.instagramclone.repository.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.repositories.PopularTvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retroApis: RetroApis,
    private val popularTvShowsRepository: PopularTvShowsRepository
): ViewModel() {

    private var popularTvShowsLiveData: Flow<PagingData<TvShowData>>? = null
    public var popularMoviesLiveData: MutableLiveData<List<MovieData>> = MutableLiveData()

    init {
        Log.d(TAG, "init: ")
    }

    fun fetchMoviesList() {
        Log.d(TAG, "fetchMoviesList: ")
        val pageId = Random.nextInt(1, 20)
        retroApis.getPopularMoviesList(pageId)
            .enqueue(object : Callback<PopularMoviesResponse> {
                override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                    Log.e(TAG, "fetchMoviesList: onFailure: ", t)
                }

                override fun onResponse(
                    call: Call<PopularMoviesResponse>,
                    response: Response<PopularMoviesResponse>
                ) {
                    Log.d(TAG, "fetchMoviesList: onResponse: ")
                    onMoviesDataLoaded(response)
                }
            })
    }

    fun getPopularTvShows(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getPopularTvShows: ")
        if(popularTvShowsLiveData != null){
            return popularTvShowsLiveData!!
        }
        val newResult: Flow<PagingData<TvShowData>> =
            popularTvShowsRepository.getTvShows().cachedIn(viewModelScope)
        popularTvShowsLiveData = newResult
        return newResult
    }

    private fun onMoviesDataLoaded(response: Response<PopularMoviesResponse>) {
        Log.d(TAG, "onMoviesDataLoaded: ")
        val popularMoviesResponse: PopularMoviesResponse? = response.body()
        val movieDataList: List<MovieData>? = popularMoviesResponse?.movieData
        movieDataList?.let {
            popularMoviesLiveData.value = it
        }
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }


}