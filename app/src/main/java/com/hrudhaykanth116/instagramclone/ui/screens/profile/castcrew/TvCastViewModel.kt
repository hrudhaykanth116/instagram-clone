package com.hrudhaykanth116.instagramclone.ui.screens.profile.castcrew

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.hrudhaykanth116.instagramclone.data.models.GetTvCreditsResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TvCastViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): ViewModel() {

    private val _tvId by lazy {
        MutableLiveData<Int?>(null)
    }

    @MainThread
    fun setTvId(tvId: Int) {
        if(tvId != _tvId.value){
            _tvId.value = tvId
        }
    }

    val tvCreditsResponseLiveData: LiveData<Resource<GetTvCreditsResponse>> = _tvId.switchMap { tvId ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(
                tvShowsRepository.getTvCredits(
                    tvId!!
                )
            )
        }
    }

    companion object{
        private const val TAG = "TvImagesViewModel"
    }

}