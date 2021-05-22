package com.hrudhaykanth116.instagramclone.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hrudhaykanth116.instagramclone.adapters.ActivityFragmentAdapter
import com.hrudhaykanth116.instagramclone.databinding.ActivityFragmentBinding
import com.hrudhaykanth116.instagramclone.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ActivityFragment : Fragment() {

    private lateinit var binding: ActivityFragmentBinding
    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        binding.progressBar.visibility = View.VISIBLE

        val pageId = Random.nextInt(1, 10)
        RetroApiClient.getRetroApiService().getTopRatedTvShows(pageId)
            .enqueue(object : Callback<TvShowDataPagedResponse> {
                override fun onFailure(call: Call<TvShowDataPagedResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    handleError()
                }

                override fun onResponse(
                    call: Call<TvShowDataPagedResponse>,
                    response: Response<TvShowDataPagedResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    val tvShowDataPagedResponse = response.body()
                    val tvShowsList = tvShowDataPagedResponse?.tvShowsList
                    if (tvShowsList != null) {
                        binding.activityRecyclerView.adapter = ActivityFragmentAdapter(tvShowsList)
                    } else {
                        handleError()
                    }
                }
            })

    }

    private fun handleError() {
        Toast.makeText(context, "Error loading show list", Toast.LENGTH_SHORT).show();
    }

}
