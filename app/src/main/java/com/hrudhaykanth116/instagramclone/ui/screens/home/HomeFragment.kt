package com.hrudhaykanth116.instagramclone.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.databinding.FragmentHomeBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.ui.adapters.PagingLoadStateAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private var popularTvShowsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
//        lifecycleScope.launchWhenStarted {
//            homeViewModel.fetchMoviesList()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        binding.shimmerFrameLayout.startShimmer()
        initViews()
        initObservers()
        getPopularTvShows()
        homeViewModel.fetchMoviesList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        initMainPostsRecyclerView()

        binding.mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
    }

    private fun initObservers() {
        Log.d(TAG, "initObservers: ")

        homeViewModel.popularMoviesLiveData.observe(viewLifecycleOwner){
            homeFragmentAdapter.updateMovieDataList(it)
        }

    }

    private fun initMainPostsRecyclerView() {
        Log.d(TAG, "initMainPostsRecyclerView: ")

        homeFragmentAdapter = HomeFragmentAdapter(object : HomeFragmentAdapter.IPostClickListener {
            override fun onProfileNameClicked(tvShowData: TvShowData) {
                tvShowData.id?.let {
                    val tvShowFragmentAction = HomeFragmentDirections.actionTvShowFragment(
                        it
                    )
                    findNavController().navigate(tvShowFragmentAction)
                } ?: run{
                    ToastHelper.showErrorToast(requireContext(), "No tv id.")
                }
            }
        })

        binding.homePopularTvShowsRV.adapter = homeFragmentAdapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter { homeFragmentAdapter.retry() }
        )

        homeFragmentAdapter.addLoadStateListener { combinedLoadStates ->
            onLoadStateChanged(combinedLoadStates)
        }

//        binding.homePopularTvShowsRV.recycledViewPool.setMaxRecycledViews(HomeFragmentAdapter.TYPE_PUBLIC_STORIES, 1)
    }

    private fun onLoadStateChanged(combinedLoadStates: CombinedLoadStates) {
        Log.d(TAG, "onLoadStateChanged: loadState.source: ${combinedLoadStates.source}")

        // refresh --> load overall(When initial load, adapter.refresh, adapter.retry)
        // append --> load state of end of list(When scrolled to last item in paging data)
        // prepend --> load state of list beginning(like refresh state and may be when prevKey needs to be loaded.)

        // Only show the list if refresh succeeds.
        // binding.homePopularTvShowsRV.isVisible = loadState.source.refresh is LoadState.NotLoading

        // Show loading spinner during initial load or refresh.
        // binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

        if (
            (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.prepend.endOfPaginationReached) ||
            combinedLoadStates.source.refresh is LoadState.Error
        ) {
            binding.mainPostsSwipeRefreshLayout.isRefreshing = false
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.isGone = true
        }

        // Show the retry state if initial load or refresh fails.
        // binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

        // Not loading and no error(Data loaded). Show empty list
        val isListEmpty =
            combinedLoadStates.refresh is LoadState.NotLoading &&
                    combinedLoadStates.prepend.endOfPaginationReached &&
                    homeFragmentAdapter.itemCount == 0

        binding.noDataTextView.isVisible = isListEmpty

        binding.dataLoadErrorView.isVisible =
            combinedLoadStates.source.refresh is LoadState.Error
                    && homeFragmentAdapter.itemCount < 1

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

    private fun refreshData() {
        Log.d(TAG, "refreshPosts: ")
        homeViewModel.refreshData()
        homeFragmentAdapter.refresh()
    }

    private fun getPopularTvShows() {
        Log.d(TAG, "getPopularTvShows: ")
        // Make sure we cancel the previous job before creating a new one
        popularTvShowsJob?.cancel()
        popularTvShowsJob = lifecycleScope.launchWhenStarted {
            Log.d(TAG, "getPopularTvShows: launchWhenStarted")
            homeViewModel.getPopularTvShows().collectLatest { tvShowPagingData: PagingData<TvShowData> ->
                Log.d(TAG, "getPopularTvShows: collectLatest")
                homeFragmentAdapter.submitData(tvShowPagingData)
            }
        }
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}
