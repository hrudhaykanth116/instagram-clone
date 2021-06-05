package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.databinding.MovieStoryViewItemBinding
import com.hrudhaykanth116.instagramclone.repository.models.MovieData
import com.hrudhaykanth116.instagramclone.ui.screens.moviestories.MoviesStoriesActivity
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader


class PublicStoryThumbnailAdapter(private val movieDataList: ArrayList<MovieData>) :
    RecyclerView.Adapter<PublicStoryThumbnailAdapter.PublicStoryViewHolder>() {

    private val MY_STORY_VIEW_TYPE = 0
    private val PUBLIC_STORY_VIEW_TYPE = 1

    class PublicStoryViewHolder(private val binding: MovieStoryViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        public fun bind(
            movieData: MovieData,
        ) {
            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + movieData.posterPath

            ImageLoader.load(imgUrl, binding.innerImg)

            // binding.addIcon.visibility = View.VISIBLE
            binding.description.text = movieData.title


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
        val binding = MovieStoryViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

//        publicStoryViewHolder.setIsRecyclable(false)

        val movieData: MovieData = movieDataList[position]
        publicStoryViewHolder.bind(movieData)

        val itemView = publicStoryViewHolder.itemView
        itemView.setOnClickListener {
            MoviesStoriesActivity.start(itemView.context, movieDataList, position)
        }
    }


}