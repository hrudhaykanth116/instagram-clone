package com.hrudhaykanth116.instagramclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.repository.datasources.PopularMoviesDataFactory
import java.util.concurrent.Executors


class HomeViewModel : ViewModel() {

    public var networkState: LiveData<NetworkState>
    public var moviesLiveData: LiveData<PagedList<MovieData>>

    init {

        val moviesDataFactory = PopularMoviesDataFactory()
        // When data source changes in factory, network state live data is also updated.
        networkState = Transformations.switchMap(moviesDataFactory.popularMovieDSLiveData) {
            it.networkState
        }

        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10) // x items will be loaded from data source. after getting last set(last x), loadAfter() is called.
            .build()

        // x threads will be used to execute data loading one after the other.iterates again.
        val executor = Executors.newFixedThreadPool(3)
        moviesLiveData  = LivePagedListBuilder<Int, MovieData>(moviesDataFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()


    }


}