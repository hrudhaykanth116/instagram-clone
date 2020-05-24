package com.hrudhaykanth116.instagramclone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.MainPostsAdapter
import com.hrudhaykanth116.instagramclone.models.UserPostsData
import com.hrudhaykanth116.instagramclone.network.ApiClient
import com.hrudhaykanth116.instagramclone.network.RetroApi
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var retrofit: Retrofit? = null
    private var apiClient: RetroApi? = null
    private var userPostsJsonCall: Call<UserPostsData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRetrofit()
        initMainPostsRecyclerView(view)

        mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshList()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initMainPostsRecyclerView(view: View) {
        val testList = ArrayList<String>()
        for (i in 1..20) {
            testList.add("Item: $i")
        }
        view.main_posts_rv.adapter = MainPostsAdapter(testList)


        val callback = object : Callback<UserPostsData?> {
            override fun onFailure(call: Call<UserPostsData?>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<UserPostsData?>,
                response: Response<UserPostsData?>
            ) {
                Log.i(TAG, "onResponse: ")
                val userPostsData = response.body()
                val userPosts = userPostsData?.userPosts
            }

        }
        userPostsJsonCall?.enqueue(callback)

    }

    private fun initRetrofit() {
        retrofit = ApiClient.getClient()
        apiClient = retrofit?.create(RetroApi::class.java)
        userPostsJsonCall = apiClient?.getUserPostsJson()
    }

    private fun refreshList() {
        val callback = object : Callback<UserPostsData?> {
            override fun onFailure(call: Call<UserPostsData?>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
                Toast.makeText(context, "Error retrieving posts", Toast.LENGTH_SHORT).show()
                mainPostsSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<UserPostsData?>,
                response: Response<UserPostsData?>
            ) {
                Log.i(TAG, "onResponse: ")
                Toast.makeText(context, "Successfully retrieved posts", Toast.LENGTH_SHORT).show()
                mainPostsSwipeRefreshLayout.isRefreshing = false
                val userPostsData = response.body()
                val userPosts = userPostsData?.userPosts
            }

        }
        userPostsJsonCall?.clone()?.enqueue(callback)
    }

    companion object{
        private val TAG = HomeFragment::class.java.simpleName
    }

}
