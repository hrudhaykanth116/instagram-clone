package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.MovieData
import kotlinx.android.synthetic.main.movie_stories_fragment.view.*

class MoviePagerAdapter(private val movieDataList: ArrayList<MovieData>) :
    RecyclerView.Adapter<MoviePagerAdapter.MoviePagerViewHolder>() {

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_stories_fragment, parent, false)
        return MoviePagerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviePagerViewHolder, position: Int) {
        val movieData = movieDataList[position]
        holder.bind(movieData)
    }


    class MoviePagerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val movieNameTv: TextView = view.movieNameTv
        private val mainImgView: ImageView = view.mainImgView


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