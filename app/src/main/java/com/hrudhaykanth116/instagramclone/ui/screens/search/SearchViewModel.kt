package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.repository.models.NetworkState
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.repositories.PopularTvShowsRepository
import com.hrudhaykanth116.instagramclone.repository.repositories.TopRatedTvShowsRepository
import com.hrudhaykanth116.instagramclone.ui.screens.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val topRatedTvShowsRepository: TopRatedTvShowsRepository,
) : ViewModel() {

    private var topRatedTvShowsLiveData: Flow<PagingData<TvShowData>>? = null

    init {
        Log.d(TAG, "init: ")
    }

    fun getTopRatedTvShows(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getPopularTvShows: ")
        val newResult: Flow<PagingData<TvShowData>> = topRatedTvShowsRepository.getTvShows()
        topRatedTvShowsLiveData = newResult
        return topRatedTvShowsLiveData!!
    }

    companion object{
        private const val TAG = "SearchViewModel"
    }

}