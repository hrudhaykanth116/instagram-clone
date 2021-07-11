package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.instagramclone.data.models.discover.DiscoverResult
import com.hrudhaykanth116.instagramclone.data.models.genres.Genre
import com.hrudhaykanth116.instagramclone.data.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.DiscoverTvShowsRepository
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TopRatedTvShowsRepository
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.instagramclone.utils.extensions.livedata.contentEquals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val topRatedTvShowsRepository: TopRatedTvShowsRepository,
    private val discoverTvShowsRepository: DiscoverTvShowsRepository,
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    private var discoverTvShowsFlowData: Flow<PagingData<DiscoverResult>>? = null

    val tvGenresResponseResource = MutableLiveData<Resource<GetTvGenresResponse>>()

    var selectedGenres: ArrayList<Genre>? = null

    var resource: Resource<GetTvGenresResponse>? = null

    init {
        Log.d(TAG, "init: ")
    }

    fun discoverTvShows(genres: ArrayList<Genre>?): Flow<PagingData<DiscoverResult>> {
        Log.d(TAG, "discoverTvShows: ")
        val selectedGenresChanged = !selectedGenres?.toSet().contentEquals(genres?.toSet())
        if (!selectedGenresChanged && discoverTvShowsFlowData != null) {
            return discoverTvShowsFlowData!!
        }
        if (genres != null) {
            selectedGenres = arrayListOf()
            selectedGenres?.addAll(genres)
        }else{
            selectedGenres = null
        }
        val newResult: Flow<PagingData<DiscoverResult>> =
            discoverTvShowsRepository.getTvShowsPagingData(genres).cachedIn(viewModelScope)
        discoverTvShowsFlowData = newResult
        return discoverTvShowsFlowData!!
    }

    @WorkerThread
    suspend fun getTvGenres(): Resource<GetTvGenresResponse> {
        if (resource == null) {
            resource = tvShowsRepository.getTvGenres()
        }
        return resource!!
    }

    companion object{
        private const val TAG = "SearchViewModel"
    }

}