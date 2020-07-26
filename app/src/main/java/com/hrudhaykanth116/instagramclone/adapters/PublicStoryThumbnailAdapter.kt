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
import kotlinx.android.synthetic.main.rounded_image_container.view.*
import kotlinx.android.synthetic.main.stories_view_item.view.*


class PublicStoryThumbnailAdapter(private val movieDataList: List<MovieData>) :
    RecyclerView.Adapter<PublicStoryThumbnailAdapter.PublicStoryViewHolder>() {

    private val MY_STATUS_VIEW_TYPE = 0
    private val PUBLIC_STATUS_VIEW_TYPE = 1

    class PublicStoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageView: ImageView = itemView.innerImg as ImageView
        private val addIcon: ImageView = itemView.myStatusAddIcon as ImageView
        private val description: TextView = itemView.description as TextView

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
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicStoryThumbnailAdapter.PublicStoryViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.stories_view_item, parent, false)
        return PublicStoryThumbnailAdapter.PublicStoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            MY_STATUS_VIEW_TYPE
        } else {
            PUBLIC_STATUS_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(
        publicStoryViewHolder: PublicStoryThumbnailAdapter.PublicStoryViewHolder,
        position: Int
    ) {
        // val imgUrl = "https://picsum.photos/id/${holder.adapterPosition * 15}/300"

        val movieData: MovieData = movieDataList[position]
        publicStoryViewHolder.bind(movieData, publicStoryViewHolder)
    }


}