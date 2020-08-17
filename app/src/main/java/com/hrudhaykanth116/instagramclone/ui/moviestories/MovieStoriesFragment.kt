package com.hrudhaykanth116.instagramclone.ui.moviestories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hrudhaykanth116.instagramclone.R

class MovieStoriesFragment : Fragment() {



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