package com.hrudhaykanth116.instagramclone.ui.screens.profile.similar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowSimilarShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvSimilarShowsViewModel @Inject constructor(
    private val tvShowSimilarShowsRepository: TvShowSimilarShowsRepository
): ViewModel() {

    private var discoverTvShowsFlowData: Flow<PagingData<TvShowData>>? = null

    fun getTvShowsPagingData(tvShowId: Int): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "discoverTvShows: ")
        if (discoverTvShowsFlowData != null) {
            return discoverTvShowsFlowData!!
        }
        val newResult: Flow<PagingData<TvShowData>> =
            tvShowSimilarShowsRepository.getTvShowsPagingData(tvShowId).cachedIn(viewModelScope)
        discoverTvShowsFlowData = newResult
        return discoverTvShowsFlowData!!
    }

    companion object{
        private const val TAG = "TvImagesViewModel"
    }

}