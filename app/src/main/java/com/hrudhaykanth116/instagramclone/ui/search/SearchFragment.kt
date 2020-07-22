package com.hrudhaykanth116.instagramclone.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.SearchCategoriesAdapter
import com.hrudhaykanth116.instagramclone.adapters.SearchResultsAdapter
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.search_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initMainPostsRecyclerView()
        initViewModel()
        initCategoriesRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initCategoriesRecyclerView() {

        val testList = ArrayList<String>()
        for (i in 1..60) {
            testList.add("Item: $i")
        }

        categories.adapter = SearchCategoriesAdapter(testList)
        categories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun initViewModel() {
        val searchViewModel: SearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.topRatedTvShowsLiveData.observe(viewLifecycleOwner,
            Observer<PagedList<TvShowData>> { pagedList ->
                // Data source changed.
                searchResultsAdapter.submitList(pagedList)
            }
        )
        searchViewModel.networkState.observe(viewLifecycleOwner,
            Observer { networkState ->
//                searchResultsAdapter.setNetworkState(networkState)
                Log.i(TAG, "initViewModel: ")
            }
        )
    }

    private fun initMainPostsRecyclerView() {

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        searchResultsAdapter = SearchResultsAdapter()
        searchResultsContainer.adapter = searchResultsAdapter
        searchResultsContainer.layoutManager = staggeredGridLayoutManager


    }

    companion object{
        private val TAG = SearchFragment::class.java.simpleName
    }

}
