package com.hrudhaykanth116.instagramclone.ui.screens.profile.reviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.data.models.GetTvReviewsResponse
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvReviewsViewModel @Inject constructor(
    private val tvShowReviewsRepository: TvShowReviewsRepository
): ViewModel() {

    private var discoverTvShowsFlowData: Flow<PagingData<GetTvReviewsResponse.ReviewDetails>>? = null

    fun getTvShowsPagingData(tvShowId: Int): Flow<PagingData<GetTvReviewsResponse.ReviewDetails>> {
        Log.d(TAG, "discoverTvShows: ")
        if (discoverTvShowsFlowData != null) {
            return discoverTvShowsFlowData!!
        }
        val newResult: Flow<PagingData<GetTvReviewsResponse.ReviewDetails>> =
            tvShowReviewsRepository.getTvShowsPagingData(tvShowId).cachedIn(viewModelScope)
        discoverTvShowsFlowData = newResult
        return discoverTvShowsFlowData!!
    }

    companion object{

        private const val TAG = "TvReviewsViewModel"

    }

}