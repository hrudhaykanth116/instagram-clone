package com.hrudhaykanth116.instagramclone.ui.screens.moviestories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieStoriesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_stories_fragment, container, false)
    }

    companion object{

        public fun getInstance(): Fragment {
            val movieStoriesFragment = MovieStoriesFragment()
            val bundle: Bundle = Bundle()
            bundle.putString("movie", "test")
            movieStoriesFragment.arguments = bundle
            return movieStoriesFragment
        }

    }

}