package com.hrudhaykanth116.instagramclone.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.HomeFragmentAdapter
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.post_view_item.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userDpView: ImageView = itemView.dpView.innerImg as ImageView
    val contentTV: ImageView = itemView.postContent as ImageView
    val userNameTV: TextView = itemView.postUserNameTV as TextView
    val likedDescriptionTV: TextView = itemView.likedDescriptionTV as TextView
    val captionTV: TextView = itemView.captionTV as TextView

    public fun bind(
        tvShowData: TvShowData,
        postClickListener: HomeFragmentAdapter.IPostClickListener
    ) {
        val postImgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.backdropPath
        val postDpUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
        val context = itemView.context

        userNameTV.text = tvShowData.originalName
        userNameTV.setOnClickListener {
            postClickListener.onProfileNameClicked(tvShowData.id)
        }

        likedDescriptionTV.text = context.getString(R.string.likes, tvShowData.voteCount.toString())
        val captionTV = captionTV
        captionTV.text = context.getString(R.string.post_caption, tvShowData.originalName, tvShowData.overview)
        captionTV.post {
            if (captionTV.lineCount > 2){
                captionTV.setLines(2)
            }
        }

        loadImg(context, postImgUrl, contentTV)
        loadImg(context, postDpUrl, userDpView)

    }

    private fun loadImg(
        context: Context,
        postImgUrl: String,
        imageView: ImageView
    ) {
        Glide
            .with(context)
            .load(postImgUrl)
            .into(imageView)
    }

}