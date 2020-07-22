package com.hrudhaykanth116.instagramclone.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.repository.datasources.TopRatedTvShowsDataFactory
import java.util.concurrent.Executors

class SearchViewModel : ViewModel() {

    public var networkState: LiveData<NetworkState>
    public var topRatedTvShowsLiveData: LiveData<PagedList<TvShowData>>

    init {

        val topRatedTvShowsDataFactory = TopRatedTvShowsDataFactory()
        // When data source changes in factory, network state live data is also updated.
        networkState =
            Transformations.switchMap(topRatedTvShowsDataFactory.topRatedTvShowsDataSourceLiveData) {
                it.networkState
            }

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