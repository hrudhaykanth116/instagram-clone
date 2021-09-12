package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.data.models.genres.Genre
import com.hrudhaykanth116.instagramclone.data.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentSearchScreenBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.SearchCategoriesAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchScreenBinding

    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val searchScreenViewModel: SearchScreenViewModel by viewModels()

    private var fetchTopRatedTvShowsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentSearchScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initViews()
        binding.progressBar.isVisible = true
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ")
        // Need to cancel the job as collect is suspendable on lifecycle scope
        // fetchTopRatedTvShowsJob?.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    private fun initViews() {
        initClickListeners()
        initSearchResultsRecyclerView()
        initCategoriesRecyclerView()
    }

    private fun initClickListeners() {

        binding.searchTextView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        searchResultsAdapter.refresh()
    }

    private fun initCategoriesRecyclerView() {


        // searchScreenViewModel.getTvGenres()
        //     .observe(viewLifecycleOwner) { tvGenresResponseResource: Resource<GetTvGenresResponse>? ->

        lifecycleScope.launch {
            binding.progressBar.isVisible = true

            val searchCategories = ArrayList<Genre>()
            val tvGenresResponseResource: Resource<GetTvGenresResponse> = searchScreenViewModel.getTvGenres()
            if (tvGenresResponseResource.isSuccessful) {
                tvGenresResponseResource.data?.genres?.let { searchCategories.addAll(it) }

                val categoriesAdapter = SearchCategoriesAdapter(searchCategories).apply {

                    mSelectedCategories.observe(viewLifecycleOwner) { genresList: ArrayList<Genre>? ->
                        // TODO: 12/07/21 First time, this observer and regular refresh methods are called. 
                        refreshDiscoverTvShows(genresList)
                    }
                    updateSelectedCategories(searchScreenViewModel.selectedGenres)
                }
                binding.categories.adapter = categoriesAdapter
                binding.categories.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                refreshDiscoverTvShows(searchScreenViewModel.selectedGenres)
            }else{
                ToastHelper.showErrorToast(requireContext(), "Categories api failed.")
            }
            binding.progressBar.isVisible = false
        }

    }

    private fun initSearchResultsRecyclerView() {
        Log.d(TAG, "initSearchResultsRecyclerView: ")

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        searchResultsAdapter = SearchResultsAdapter(){ tvShowData ->
            tvShowData.id?.let {
                val tvShowFragmentAction = SearchScreenFragmentDirections.actionTvShowFragment(it)
                findNavController().navigate(tvShowFragmentAction)
            } ?: run{
                ToastHelper.showErrorToast(requireContext(), "No tv show id")
            }
        }
        binding.searchResultsContainer.adapter = searchResultsAdapter
        binding.searchResultsContainer.layoutManager = staggeredGridLayoutManager

        searchResultsAdapter.addLoadStateListener { combinedLoadStates ->
            onLoadStateChanged(combinedLoadStates)
        }

    }

    private fun onLoadStateChanged(combinedLoadStates: CombinedLoadStates) {

        if (
            (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.prepend.endOfPaginationReached) ||
            combinedLoadStates.source.refresh is LoadState.Error
        ) {

            // FIRST LOAD COMPLETED(either success or error)

            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.isVisible = false
        }

        // Not loading and no error(Data loaded). Show empty list
        val isListEmpty =
            combinedLoadStates.refresh is LoadState.NotLoading &&
                    combinedLoadStates.prepend.endOfPaginationReached &&
                    searchResultsAdapter.itemCount == 0

        binding.noDataTextView.isVisible = isListEmpty


        binding.dataLoadErrorView.isVisible =
            combinedLoadStates.source.refresh is LoadState.Error
                    && searchResultsAdapter.itemCount < 1

        val refreshDataLoadError = combinedLoadStates.source.refresh as? LoadState.Error?
        refreshDataLoadError?.let {
            Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT).show()
        }

        // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error
            ?: combinedLoadStates.append as? LoadState.Error
            ?: combinedLoadStates.prepend as? LoadState.Error

        errorState?.let {
            Log.e(TAG, "onLoadStateChanged: error: ", it.error)
        }

    }

    private fun refreshDiscoverTvShows(genres: ArrayList<Genre>? = null) {
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        fetchTopRatedTvShowsJob?.cancel()
        fetchTopRatedTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            searchScreenViewModel.discoverTvShows(genres)
                .collectLatest { tvShowPagingData: PagingData<TvShowData> ->
                    Log.d(TAG, "getPopularTvShows: collectLatest")
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressBar.isVisible = false
                    searchResultsAdapter.submitData(tvShowPagingData)
                }
        }
    }

    companion object {
        private val TAG = SearchScreenFragment::class.java.simpleName
    }

}
