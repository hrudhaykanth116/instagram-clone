package com.hrudhaykanth116.instagramclone.ui.screens.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.instagramclone.data.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.AiringTodayShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val airingTodayShowsRepository: AiringTodayShowsRepository
) : ViewModel() {

    private val _airingTodayShowsLiveData: MutableLiveData<Resource<TvShowDataPagedResponse>> = MutableLiveData()
    val airingTodayShowsLiveData: LiveData<Resource<TvShowDataPagedResponse>> = _airingTodayShowsLiveData

    fun getAiringTodayShows(pageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val airingTodayShows: Resource<TvShowDataPagedResponse> = airingTodayShowsRepository.getAiringTodayShows(pageId)
            _airingTodayShowsLiveData.postValue(airingTodayShows)
        }
    }

}
