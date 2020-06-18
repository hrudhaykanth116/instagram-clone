package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import kotlinx.android.synthetic.main.rounded_image_container.view.*
import kotlinx.android.synthetic.main.stories_view_item.view.*


class PublicStoryThumbnailAdapter(private val list: List<String>): RecyclerView.Adapter<PublicStoryThumbnailAdapter.ViewHolder>() {

    private val MY_STATUS_VIEW_TYPE = 0
    private val PUBLIC_STATUS_VIEW_TYPE = 1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImageView = itemView.innerImg
        val myStatusAddIcon = itemView.myStatusAddIcon
        val profileName = itemView.profile_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
            inflate(R.layout.stories_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            MY_STATUS_VIEW_TYPE
        }else {
            PUBLIC_STATUS_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = "https://picsum.photos/id/${holder.adapterPosition * 15}/300"

        Glide.with(holder.itemView).load(url).into(holder.thumbnailImageView)
        if (holder.itemViewType == MY_STATUS_VIEW_TYPE) {
            holder.myStatusAddIcon.visibility = View.VISIBLE
            holder.profileName.text = "Rishi kanth"
        }
    }

}