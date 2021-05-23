package com.hrudhaykanth116.instagramclone.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hrudhaykanth116.instagramclone.repository.models.NetworkState
import com.hrudhaykanth116.instagramclone.repository.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.datasources.TopRatedTvShowsDataFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val topRatedTvShowsDataFactory: TopRatedTvShowsDataFactory
) : ViewModel() {

    public var networkState: LiveData<NetworkState> =
        Transformations.switchMap(topRatedTvShowsDataFactory.topRatedTvShowsDataSourceLiveData) {
            it.networkState
        }

    public var topRatedTvShowsLiveData: LiveData<PagedList<TvShowData>>

    init {

        // When data source changes in factory, network state live data is also updated.

        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(18)
            .setPageSize(9) // x items will be loaded from data source. after getting last set(last x), loadAfter() is called.
            .build()

        // x threads will be used to execute data loading one after the other.iterates again.
        val executor = Executors.newFixedThreadPool(3)
        topRatedTvShowsLiveData =
            LivePagedListBuilder<Int, TvShowData>(topRatedTvShowsDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()


    }

}