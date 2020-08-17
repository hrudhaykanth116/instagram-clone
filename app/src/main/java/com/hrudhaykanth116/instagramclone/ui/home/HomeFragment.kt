package com.hrudhaykanth116.instagramclone.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import com.hrudhaykanth116.instagramclone.network.RetroApis
import com.hrudhaykanth116.instagramclone.repository.databases.AppDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call


class HomeFragment : Fragment() {

    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var apisClient: RetroApis
    private lateinit var tvShowDetailsCall: Call<TvShowDataPagedResponse>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val appDatabase = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app-db"
        ).allowMainThreadQueries().build()

        this.apisClient = RetroApiClient.getRetroApiService()

        initMainPostsRecyclerView()
        initViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.popularTvShowsLiveData.observe(viewLifecycleOwner,
            Observer<PagedList<TvShowData>> { pagedList ->
                // Data source changed.
                homeFragmentAdapter.submitList(pagedList)
            }
        )
        homeViewModel.networkState.observe(viewLifecycleOwner,
            Observer { networkState ->
                homeFragmentAdapter.setNetworkState(networkState)
            }
        )

        homeViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            homeFragmentAdapter.updateMovieDataList(it)
        })

    }

    private fun initMainPostsRecyclerView() {
        homeFragmentAdapter = HomeFragmentAdapter(object : HomeFragmentAdapter.IPostClickListener {
            override fun onProfileNameClicked(tvShowData: TvShowData) {
                val tvShowFragmentAction = HomeFragmentDirections.actionTvShowFragment(tvShowData)
                findNavController().navigate(tvShowFragmentAction)
            }

        })
        homePopularTvShowsRV.layoutManager = LinearLayoutManager(context)
        homePopularTvShowsRV.adapter = homeFragmentAdapter

        mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshPosts()
        }

    }

    private fun refreshPosts() {

        homeViewModel.refreshData()
        Handler().postDelayed(Runnable {
            mainPostsSwipeRefreshLayout.isRefreshing = false
        }, 1000)

        /*val callback = object : Callback<TvShowDataPagedResponse> {
            override fun onFailure(call: Call<TvShowDataPagedResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
                context?.let {
                    Toast.makeText(it, "Error retrieving posts", Toast.LENGTH_SHORT).show()
                }
                mainPostsSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<TvShowDataPagedResponse>,
                response: Response<TvShowDataPagedResponse>
            ) {
                Log.i(TAG, "onResponse: ")
                Toast.makeText(context, "No new posts", Toast.LENGTH_SHORT).show()
                mainPostsSwipeRefreshLayout.isRefreshing = false
            }

        }
        tvShowDetailsCall = apisClient.getPopularTvShows(1)
        tvShowDetailsCall.clone().enqueue(callback)*/


    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}
