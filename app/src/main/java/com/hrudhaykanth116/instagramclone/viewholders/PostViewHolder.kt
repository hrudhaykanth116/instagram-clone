package com.hrudhaykanth116.instagramclone.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_view_item.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userDpView: ImageView = itemView.dpView.innerImg as ImageView
    val contentTV: ImageView = itemView.postContent as ImageView
    val userNameTV: TextView = itemView.postUserNameTV as TextView
    val likedDescriptionTV: TextView = itemView.likedDescriptionTV as TextView
    val captionTV: TextView = itemView.captionTV as TextView
}