package com.hrudhaykanth116.instagramclone.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hrudhaykanth116.instagramclone.data.constants.AppConstants
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.databinding.FragmentShowImagesBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment

class TvSimilarShowFragment : BaseFragment() {

    lateinit var binding: FragmentShowImagesBinding

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
            it.getParcelable<TvShowDetails>(KEY_TV_SHOW_DETAILS)?.let { tvShowDetails: TvShowDetails ->
                initViews(tvShowDetails)
            }
        }
    }

    private fun initViews(tvShowDetails: TvShowDetails) {

    }

    companion object{

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