package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.main_post_item.view.*
import kotlinx.android.synthetic.main.main_status_layout.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*

class MainPostsAdapter :
    PagedListAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {

    private val TYPE_PUBLIC_STATUS = 1
    private val TYPE_POST = 2
    private val TYPE_PROGRESS = 3

    private var currentNetworkState: NetworkState = NetworkState.LOADING

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userDpView: ImageView = itemView.dpView.innerImg as ImageView
        val contentTV: ImageView = itemView.postContent as ImageView
        val userNameTV: TextView = itemView.postUserNameTV as TextView
        val likedDescriptionTV: TextView = itemView.likedDescriptionTV as TextView
        val captionTV: TextView = itemView.captionTV as TextView
    }

    class PublicStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val publicStoriesContainer = itemView.public_stories_rv
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView
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
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_status_layout, parent, false)
                viewHolder = PublicStatusViewHolder(itemView)
            }
            TYPE_POST -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_post_item, parent, false)
                viewHolder = PostViewHolder(itemView)
            }
            TYPE_PROGRESS -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_bar_row, parent, false)
                viewHolder = ProgressViewHolder(itemView)
            }
            else -> {
                throw Exception("Wrong view type")
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        val pagedListCount = super.getItemCount()
        return pagedListCount + if (shouldShowProgressIcon()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && shouldShowProgressIcon()) {
            // If last view and should show network state view
            return TYPE_PROGRESS
        }
        return when (position) {
            0 -> TYPE_POST
            else -> TYPE_POST
        }
    }

    public fun setNetworkState(newNetworkState: NetworkState) {
        val isProgressIconShown = currentNetworkState != NetworkState.LOADED
        val previousState = currentNetworkState

        val shouldShowProgressIcon = newNetworkState != NetworkState.LOADED
        currentNetworkState = newNetworkState

        if (shouldShowProgressIcon) {
            if(!isProgressIconShown){
                notifyItemInserted(itemCount)
            }else if(previousState !== newNetworkState){
                notifyItemChanged(itemCount - 1)
            }
        }else if (isProgressIconShown){
            notifyItemRemoved(itemCount)
        }
    }

    private fun shouldShowProgressIcon() = currentNetworkState != NetworkState.LOADED

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_PUBLIC_STATUS -> {
                PublicStatusViewFiller().fillPublicStatusView(viewHolder as PublicStatusViewHolder)
            }
            TYPE_POST -> {
                val tvShowData = getItem(position) as TvShowData
                UserPostViewFiller().fillPostView(viewHolder as PostViewHolder, tvShowData)
            }
            TYPE_PROGRESS -> {
                val progressViewHolder = viewHolder as ProgressViewHolder
            }
            else -> {
                throw Exception("Wrong view type")
            }
        }

    }

    override fun submitList(pagedList: PagedList<TvShowData>?) {
        super.submitList(pagedList)
    }
}