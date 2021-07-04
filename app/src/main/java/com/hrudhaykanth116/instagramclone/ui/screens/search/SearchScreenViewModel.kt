package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.repository.repositories.TopRatedTvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val topRatedTvShowsRepository: TopRatedTvShowsRepository,
) : ViewModel() {

    private var topRatedTvShowsLiveData: Flow<PagingData<TvShowData>>? = null

    init {
        Log.d(TAG, "init: ")
    }

    fun getTopRatedTvShows(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getPopularTvShows: ")
        if (topRatedTvShowsLiveData != null) {
            return topRatedTvShowsLiveData!!
        }
        val newResult: Flow<PagingData<TvShowData>> =
            topRatedTvShowsRepository.getTvShowsPagingData().cachedIn(viewModelScope)
        topRatedTvShowsLiveData = newResult
        return topRatedTvShowsLiveData!!
    }

    companion object{
        private const val TAG = "SearchViewModel"
    }

}