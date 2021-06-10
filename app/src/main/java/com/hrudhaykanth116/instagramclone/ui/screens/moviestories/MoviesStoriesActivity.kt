package com.hrudhaykanth116.instagramclone.ui.screens.moviestories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hrudhaykanth116.instagramclone.data.models.MovieData
import com.hrudhaykanth116.instagramclone.databinding.ActivityMoviesStoriesBinding
import com.hrudhaykanth116.instagramclone.ui.adapters.MoviePagerAdapter
import com.hrudhaykanth116.instagramclone.ui.animations.CubeTransformer
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseActivity

class MoviesStoriesActivity : BaseActivity() {

    val binding: ActivityMoviesStoriesBinding by lazy {
        ActivityMoviesStoriesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val extras = intent.extras
        val movieDataList: ArrayList<MovieData>? = extras?.getParcelableArrayList<MovieData>(KEY_MOVIE_DATA_LIST)
        val selectedPosition: Int = extras?.getInt(KEY_SELECTED_POSITION) ?: 0

        movieDataList?.let {
            val moviePagerAdapter = MoviePagerAdapter(it)
            binding.movieViewPager.adapter = moviePagerAdapter
            binding.movieViewPager.setCurrentItem(selectedPosition, false)
            binding.movieViewPager.setPageTransformer(CubeTransformer())
        }


    }


    companion object {

        private val KEY_MOVIE_DATA_LIST: String =  "${MoviesStoriesActivity::class.java.name}.movieData"
        private val KEY_SELECTED_POSITION: String = "${MoviesStoriesActivity::class.java.name}.selectedPosition"

        fun start(context: Context, movieDataList: ArrayList<MovieData>, position: Int) {
            Intent(context, MoviesStoriesActivity::class.java).apply {
                putExtra(KEY_MOVIE_DATA_LIST, movieDataList)
                putExtra(KEY_SELECTED_POSITION, position)
                context.startActivity(this)
            }
        }


    }

}