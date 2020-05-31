package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.models.UserPostsData
import kotlinx.android.synthetic.main.main_post_item.view.*
import kotlinx.android.synthetic.main.main_status_layout.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*


class MainPostsAdapter(private val userPosts: ArrayList<UserPostsData.UserPost>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_PUBLIC_STATUS = 1
    private val TYPE_POST = 2

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postUserDpView: ImageView = itemView.dpView.innerImg as ImageView
        val postImgView: ImageView = itemView.postContent as ImageView
        val postUserNameTV: TextView = itemView.postUserNameTV as TextView
    }

    class PublicStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val publicStoriesContainer = itemView.public_stories_rv
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = getViewHolder(parent, viewType)
        return viewHolder
    }

    private fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_PUBLIC_STATUS -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_status_layout, parent, false)
                viewHolder = PublicStatusViewHolder(itemView)
            }
            TYPE_POST -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_post_item, parent, false)
                viewHolder = PostViewHolder(itemView)
            }else -> {
                throw Exception("Wrong view type")
            }
        }

        return viewHolder
    }

    public fun addPosts(newList: List<UserPostsData.UserPost>){
        userPosts.addAll(0, newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userPosts.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TYPE_PUBLIC_STATUS
            else -> TYPE_POST
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        
        when (viewHolder.itemViewType) {
            TYPE_PUBLIC_STATUS -> {
                PublicStatusViewFiller().fillPublicStatusView(viewHolder as PublicStatusViewHolder)
            }
            TYPE_POST -> {
                UserPostViewFiller().fillPostView(viewHolder as PostViewHolder, userPosts[position])
            }
            else -> {
                throw Exception("Wrong view type")
            }
        }

    }

}