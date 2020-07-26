package com.hrudhaykanth116.instagramclone.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.ActivityFragmentAdapter
import com.hrudhaykanth116.instagramclone.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import kotlinx.android.synthetic.main.activity_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ActivityFragment : Fragment() {

    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        progressBar.visibility = View.VISIBLE

        val pageId = Random.nextInt(1, 10)
        RetroApiClient.getRetroApiService().getTopRatedTvShows(pageId)
            .enqueue(object: Callback<TvShowDataPagedResponse>{
                override fun onFailure(call: Call<TvShowDataPagedResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    handleError()
                }

                override fun onResponse(
                    call: Call<TvShowDataPagedResponse>,
                    response: Response<TvShowDataPagedResponse>
                ) {
                    progressBar.visibility = View.GONE
                    val tvShowDataPagedResponse = response.body()
                    val tvShowsList = tvShowDataPagedResponse?.tvShowsList
                    if (tvShowsList != null) {
                        activityRecyclerView.adapter = ActivityFragmentAdapter(tvShowsList)
                    }else{
                        handleError()
                    }
                }
            })

    }

    private fun handleError() {
        Toast.makeText(context, "Error loading show list", Toast.LENGTH_SHORT).show();
    }

}
