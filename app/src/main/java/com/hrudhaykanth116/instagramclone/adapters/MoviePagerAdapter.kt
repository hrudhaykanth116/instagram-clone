package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.MovieStoriesFragmentBinding
import com.hrudhaykanth116.instagramclone.models.MovieData

class MoviePagerAdapter(private val movieDataList: ArrayList<MovieData>) :
    RecyclerView.Adapter<MoviePagerAdapter.MoviePagerViewHolder>() {

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewHolder {
        val binding = MovieStoriesFragmentBinding.inflate(LayoutInflater.from(parent.context))
        return MoviePagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePagerViewHolder, position: Int) {
        val movieData = movieDataList[position]
        holder.bind(movieData)
    }


    class MoviePagerViewHolder(private val binding: MovieStoriesFragmentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val movieNameTv: TextView = binding.movieNameTv
        private val mainImgView: ImageView = binding.mainImgView


        fun bind(movieData: MovieData) {
            movieNameTv.text = movieData.title

            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + movieData.backdropPath

            Glide
                .with(itemView)
                .load(imgUrl)
                .into(mainImgView)

        }


    }

}