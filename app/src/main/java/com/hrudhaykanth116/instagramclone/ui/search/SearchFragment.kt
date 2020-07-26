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
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.SearchCategoriesAdapter
import com.hrudhaykanth116.instagramclone.adapters.SearchResultsAdapter
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    // Initial state would be loading always followed by loaded or failed
    private var networkState: NetworkState = NetworkState.LOADING
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

        initSearchResultsRecyclerView()
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
                this.networkState = networkState
                updateProgressBarVisibility(networkState != NetworkState.LOADED)
                Log.i(TAG, "initViewModel: ")
            }
        )
    }

    private fun updateLoadingState(networkState: NetworkState) {
        if(networkState == NetworkState.LOADED){
            progressBar.visibility = View.GONE
        }else{
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun initSearchResultsRecyclerView() {

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        searchResultsAdapter = SearchResultsAdapter()
        searchResultsContainer.adapter = searchResultsAdapter
        searchResultsContainer.layoutManager = staggeredGridLayoutManager

        // TODO: 25-07-2020 Add better way of handling progress bar
        searchResultsContainer.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            /*override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!searchResultsContainer.canScrollVertically(1) && networkState != NetworkState.LOADED) {
                    updateProgressBarVisibility(true)
                }else{
                    updateProgressBarVisibility(false)
                }
            }*/

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1))
                    onScrolledToBottom()
            }
        })
    }

    private fun onScrolledToBottom() {
        // TODO: 26-07-2020 Show progress bar if needed
        /*if (!searchResultsContainer.canScrollVertically(1) && networkState != NetworkState.LOADED) {
            updateProgressBarVisibility(true)
        }else{
            updateProgressBarVisibility(false)
        }*/
    }

    private fun updateProgressBarVisibility(isVisible: Boolean) {
        /*if (isVisible && !progressBar.isVisible) {
            progressBar.visibility = View.VISIBLE
        }else if(!isVisible && progressBar.isVisible){
            progressBar.visibility = View.GONE
        }*/
    }

    companion object{
        private val TAG = SearchFragment::class.java.simpleName
    }

}
