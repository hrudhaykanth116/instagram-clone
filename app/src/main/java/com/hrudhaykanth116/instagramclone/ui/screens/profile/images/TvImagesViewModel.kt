package com.hrudhaykanth116.instagramclone.ui.screens.profile.images

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.hrudhaykanth116.instagramclone.data.models.GetTvImagesResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowSimilarShowsRepository
import com.hrudhaykanth116.instagramclone.data.repository.repositories.tv.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TvImagesViewModel @Inject constructor(
    private val tvShowSimilarShowsRepository: TvShowSimilarShowsRepository,
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

    val tvImagesResponseLiveData: LiveData<Resource<GetTvImagesResponse>> = _tvId.switchMap { tvId ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(
                tvShowsRepository.getTvImages(
                    tvId!!
                )
            )
        }
    }

    companion object{
        private const val TAG = "TvImagesViewModel"
    }

}