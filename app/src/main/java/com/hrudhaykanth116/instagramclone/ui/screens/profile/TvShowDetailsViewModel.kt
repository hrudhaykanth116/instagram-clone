package com.hrudhaykanth116.instagramclone.ui.screens.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.repositories.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): ViewModel() {

    val tvShowDetailsLiveData: MutableLiveData<Resource<TvShowDetails>> = MutableLiveData()

    fun getTvShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val tvShowDetails: Resource<TvShowDetails> = tvShowsRepository.getTvShowDetails(tvShowId)
            tvShowDetailsLiveData.value = tvShowDetails
        }
    }

}