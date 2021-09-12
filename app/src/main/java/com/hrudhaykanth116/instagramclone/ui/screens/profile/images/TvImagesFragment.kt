package com.hrudhaykanth116.instagramclone.ui.screens.profile.images

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.GetTvImagesResponse
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.data.models.network.Resource
import com.hrudhaykanth116.instagramclone.databinding.FragmentShowImagesBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.TvShowImagesAdapter
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class TvImagesFragment : BaseFragment() {

    private var fetchTopRatedTvShowsJob: Job? = null

    private val tvShowImagesAdapter by lazy {
        TvShowImagesAdapter()
    }
    lateinit var binding: FragmentShowImagesBinding
    private val viewModel: TvImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowImagesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)
                ?.let { tvShowDetails: TvShowDetails ->
                    viewModel.setTvId(tvShowDetails.id!!)
                }
        }
        initViews()
        initObservers()
    }

    private fun initObservers() {

        viewModel.tvImagesResponseLiveData.observe(viewLifecycleOwner) { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    // val results: List<GetTvImagesResponse.ImageObj?> =
                    //     resource.data?.backdrops?.plus(resource.data.posters) ?: emptyList()
                    val results: ArrayList<GetTvImagesResponse.ImageObj?> = ArrayList()

                    // TODO: 13/09/21 Show back drop images
                    // val backdrops: List<GetTvImagesResponse.ImageObj?>? = resource.data?.backdrops
                    // backdrops?.let { results.addAll(it) }

                    val posters: List<GetTvImagesResponse.ImageObj?>? = resource.data?.posters
                    posters?.let { results.addAll(it) }

                    onImagesListAvailable(results)

                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }

        }

    }

    private fun onImagesListAvailable(results: List<GetTvImagesResponse.ImageObj?>) {
        binding.progressBar.isVisible = false
        binding.dataLoadErrorView.isVisible = false
        binding.noDataTextView.isVisible = false

        val imagesList: List<String?> = results.map { imageObj: GetTvImagesResponse.ImageObj? ->
            imageObj?.let { MoviesDbConstants.IMAGES_BASE_URL + it.file_path }
        }
        tvShowImagesAdapter.updateList(
            imagesList
        )
    }

    private fun initViews() {
        initSearchResultsRecyclerView()
    }

    private fun initSearchResultsRecyclerView() {
        Log.d(TAG, "initSearchResultsRecyclerView: ")

        val staggeredGridLayoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.searchResultsContainer.adapter = tvShowImagesAdapter
        binding.searchResultsContainer.layoutManager = staggeredGridLayoutManager

    }

    companion object {

        private const val TAG = "TvImagesFragment"
        const val KEY_TV_SHOW_DETAILS = "${AppConstants.PACKAGE_NAME}.key_tv_show_details"

        public fun getInstance(tvShowDetails: TvShowDetails): Fragment {
            val movieStoriesFragment = TvImagesFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_TV_SHOW_DETAILS, tvShowDetails)
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}