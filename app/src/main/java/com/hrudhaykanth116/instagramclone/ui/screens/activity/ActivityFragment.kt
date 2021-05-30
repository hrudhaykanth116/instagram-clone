package com.hrudhaykanth116.instagramclone.ui.screens.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hrudhaykanth116.instagramclone.ui.adapters.ActivityFragmentAdapter
import com.hrudhaykanth116.instagramclone.databinding.ActivityFragmentBinding
import com.hrudhaykanth116.instagramclone.repository.models.TvShowDataPagedResponse
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ActivityFragment : BaseFragment() {

    private lateinit var binding: ActivityFragmentBinding
    private val viewModel: ActivityViewModel by viewModels()

    @Inject
    lateinit var retroApis: RetroApis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE

        val pageId = Random.nextInt(1, 10)
        // TODO: 30/05/21 Implement this
        /*retroApis.getTopRatedTvShows(pageId)
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
            })*/

    }

    private fun handleError() {
        Toast.makeText(context, "Error loading show list", Toast.LENGTH_SHORT).show();
    }

}
