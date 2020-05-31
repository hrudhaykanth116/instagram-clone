package com.hrudhaykanth116.instagramclone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.google.gson.Gson
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.MainPostsAdapter
import com.hrudhaykanth116.instagramclone.models.UserPostsData
import com.hrudhaykanth116.instagramclone.network.ApiClient
import com.hrudhaykanth116.instagramclone.network.RetroApi
import com.hrudhaykanth116.instagramclone.repository.databases.AppDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.InputStream


class HomeFragment : Fragment() {

    private lateinit var mainPostsAdapter: MainPostsAdapter
    private lateinit var homeViewModel: HomeViewModel
    private val userPosts: ArrayList<UserPostsData.UserPost> = ArrayList()
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

        val appDatabase = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app-db"
        ).allowMainThreadQueries().build()
        appDatabase.userDao().getAll()


        initRetrofit()
        initMainPostsRecyclerView(view)

        mainPostsSwipeRefreshLayout.setOnRefreshListener {
            refreshPosts()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initMainPostsRecyclerView(view: View) {

        mainPostsAdapter = MainPostsAdapter(userPosts)
        view.main_posts_rv.adapter = mainPostsAdapter
        view.main_posts_rv.setHasFixedSize(true)
        loadPostsFromLocal()
        refreshPosts()

    }

    private fun loadPostsFromLocal() {
        val myJson: String = getJsonFromAssets(context?.resources?.assets?.open("post-sample.json"))
        val userPostsData = Gson().fromJson(myJson, UserPostsData::class.java)
        userPostsData.userPosts?.let {
            Toast.makeText(context, "Showing local data", Toast.LENGTH_SHORT).show()
            userPosts.addAll(it)
            mainPostsAdapter.addPosts(it)
        }
    }

    private fun getJsonFromAssets(inputStream: InputStream?): String {
        return if (inputStream != null) {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        }else{
            ""
        }
    }

    private fun initRetrofit() {
        retrofit = ApiClient.getClient()
        apiClient = retrofit?.create(RetroApi::class.java)
        userPostsJsonCall = apiClient?.getUserPostsJson()
    }

    private fun refreshPosts() {
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
                userPostsData?.userPosts?.let { mainPostsAdapter.addPosts(it) }
            }

        }
        userPostsJsonCall?.clone()?.enqueue(callback)
    }

    companion object{
        private val TAG = HomeFragment::class.java.simpleName
    }

}
