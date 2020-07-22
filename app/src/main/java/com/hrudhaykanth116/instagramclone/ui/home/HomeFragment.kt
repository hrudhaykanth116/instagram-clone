package com.hrudhaykanth116.instagramclone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hrudhaykanth116.instagramclone.MainActivity
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.models.PopularMoviesResponse
import com.hrudhaykanth116.instagramclone.models.PopularTvShowsResponse
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.network.RetroApis
import com.hrudhaykanth116.instagramclone.repository.databases.AppDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var apisClient: RetroApis
    private lateinit var tvShowDetailsCall: Call<PopularTvShowsResponse>

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

        this.apisClient = (activity as MainActivity).apisClient

        initMainPostsRecyclerView(view)
        initViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        val homeViewModel: HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
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
    }

    private fun initMainPostsRecyclerView(view: View) {
        homeFragmentAdapter = HomeFragmentAdapter(object : HomeFragmentAdapter.IPostClickListener {
            override fun onProfileNameClicked(tvShowData: TvShowData) {
                val tvShowFragmentAction = HomeFragmentDirections.tvShowFragmentAction(tvShowData)
                findNavController().navigate(tvShowFragmentAction)
            }

        })
        view.main_posts_rv.layoutManager = LinearLayoutManager(context)
        view.main_posts_rv.adapter = homeFragmentAdapter

        mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshPosts()
        }

    }

    private fun refreshPosts() {
        // TODO: 13-06-2020 Create logic to get/stimulate recent movies.
        val callback = object : Callback<PopularTvShowsResponse> {
            override fun onFailure(call: Call<PopularTvShowsResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
                context?.let {
                    Toast.makeText(it, "Error retrieving posts", Toast.LENGTH_SHORT).show()
                }
                mainPostsSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<PopularTvShowsResponse>,
                response: Response<PopularTvShowsResponse>
            ) {
                Log.i(TAG, "onResponse: ")
                Toast.makeText(context, "No new posts", Toast.LENGTH_SHORT).show()
                mainPostsSwipeRefreshLayout.isRefreshing = false
            }

        }
        tvShowDetailsCall = apisClient.getPopularTvShows(1)
        tvShowDetailsCall.clone().enqueue(callback)
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}
