package com.hrudhaykanth116.instagramclone.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.models.search.TvShowSearchResults
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    private var searchTvJob: Job? = null

    private val _searchQuery: MutableLiveData<String> = MutableLiveData<String>()
    var searchQuery: String = ""
    set(value) {
        field = value
        searchTvJob?.cancel()
        if (searchQuery.isBlank()) {
            tvShowSearchList.postValue(Resource.success(null))
        }else{
            searchTvJob = viewModelScope.launch(Dispatchers.IO) {
                tvShowSearchList.postValue(Resource.loading())
                // Delay a second to listen for any more changes in search query.
                delay(1000)
                val tvShowSearchResponse: Resource<TvShowSearchResults> = tvShowsRepository.searchTvShow(searchQuery)
                tvShowSearchList.postValue(tvShowSearchResponse)
                //if (searchResponse.isSuccessful) {
                //    emit(searchResponse.body()?.searchResults)
                //}
            }
        }
    }

    //val tvShowSearchList: LiveData<Resource<TvShowSearchResults>> = _searchQuery.switchMap {
    //    Log.i("hrudhay_logs", "SearchViewModel-: _searchQuery: ${_searchQuery}")
    //    liveData {
    //    }
    //}

    val tvShowSearchList: MutableLiveData<Resource<TvShowSearchResults?>> = MutableLiveData()

    //fun setSearchQuery(searchQuery: String){
    //
    //}

}