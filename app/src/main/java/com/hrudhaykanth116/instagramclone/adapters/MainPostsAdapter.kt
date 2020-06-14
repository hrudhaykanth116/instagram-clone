package com.hrudhaykanth116.instagramclone.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.models.NetworkState
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.viewholders.PostViewHolder
import com.hrudhaykanth116.instagramclone.viewholders.ProgressViewHolder
import com.hrudhaykanth116.instagramclone.viewholders.PublicStatusViewHolder
import com.hrudhaykanth116.instagramclone.viewholders.ViewHoldersCreator

class MainPostsAdapter :
    PagedListAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {

    private val TYPE_PUBLIC_STATUS = R.layout.public_status_thumbnails_item
    private val TYPE_POST = R.layout.post_view_item
    private val TYPE_PROGRESS = R.layout.progress_bar_row

    private var currentNetworkState: NetworkState = NetworkState.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHoldersCreator.createViewHolder(parent, viewType)
    }

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

}