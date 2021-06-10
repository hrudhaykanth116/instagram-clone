package com.hrudhaykanth116.instagramclone.ui.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.data.models.MovieData
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.repository.repositories.MoviesRepository
import com.hrudhaykanth116.instagramclone.data.repository.repositories.PopularTvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val popularTvShowsRepository: PopularTvShowsRepository
): ViewModel() {

    private var popularTvShowsLiveData: Flow<PagingData<TvShowData>>? = null
    public var popularMoviesLiveData: MutableLiveData<List<MovieData>> = MutableLiveData()

    init {
        Log.d(TAG, "init: ")
    }

    fun refreshData(){
        fetchMoviesList()
    }

    fun fetchMoviesList() {
        Log.d(TAG, "fetchMoviesList: ")
        val pageId = Random.nextInt(1, 20)

        viewModelScope.launch {
            val popularMoviesResponse = moviesRepository.getPopularMoviesList(pageId)
            popularMoviesLiveData.value = popularMoviesResponse.data?.movieData
        }
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

    companion object{
        private const val TAG = "HomeViewModel"
    }


}