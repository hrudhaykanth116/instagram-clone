package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.MovieStoryViewItemBinding
import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.ui.moviestories.MoviesStoriesActivity


class PublicStoryThumbnailAdapter(private val movieDataList: ArrayList<MovieData>) :
    RecyclerView.Adapter<PublicStoryThumbnailAdapter.PublicStoryViewHolder>() {

    private val MY_STORY_VIEW_TYPE = 0
    private val PUBLIC_STORY_VIEW_TYPE = 1

    class PublicStoryViewHolder(binding: MovieStoryViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView: ImageView = binding.innerImg
        private val addIcon: ImageView = binding.myStatusAddIcon
        private val description: TextView = binding.description

        public fun bind(
            movieData: MovieData,
            publicStoryViewHolder: PublicStoryViewHolder
        ) {
            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + movieData.posterPath

            Glide
                .with(publicStoryViewHolder.itemView)
                .load(imgUrl)
                .into(publicStoryViewHolder.thumbnailImageView)

            // publicStoryViewHolder.addIcon.visibility = View.VISIBLE
            publicStoryViewHolder.description.text = movieData.title


            itemView.setOnClickListener {
//                navigateToMovieStoriesScreen(movieData, it)
            }

        }

        private fun navigateToMovieStoriesScreen(
            movieData: MovieData,
            it: View
        ) {


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicStoryThumbnailAdapter.PublicStoryViewHolder {
        val binding = MovieStoryViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return PublicStoryThumbnailAdapter.PublicStoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            MY_STORY_VIEW_TYPE
        } else {
            PUBLIC_STORY_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(
        publicStoryViewHolder: PublicStoryThumbnailAdapter.PublicStoryViewHolder,
        position: Int
    ) {

        val movieData: MovieData = movieDataList[position]
        publicStoryViewHolder.bind(movieData, publicStoryViewHolder)

        val itemView = publicStoryViewHolder.itemView
        itemView.setOnClickListener {
            MoviesStoriesActivity.start(itemView.context, movieDataList, position)
        }
    }


}