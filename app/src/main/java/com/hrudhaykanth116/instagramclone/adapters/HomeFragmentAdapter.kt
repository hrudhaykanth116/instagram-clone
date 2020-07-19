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

class HomeFragmentAdapter(private val postClickListener: IPostClickListener) :
    PagedListAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {

    private var currentNetworkState: NetworkState = NetworkState.LOADING
    private var previousNetworkState: NetworkState = NetworkState.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHoldersCreator.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_PUBLIC_STATUS -> {
                PublicStatusViewFiller().fillPublicStatusView(viewHolder as PublicStatusViewHolder)
            }
            TYPE_POST -> {
                val tvShowData: TvShowData = getItem(position - 1) as TvShowData
                val postViewHolder = viewHolder as PostViewHolder
                postViewHolder.bind(tvShowData, postClickListener)
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
        val progressBarCount = if (shouldShowProgressIcon()) 1 else 0
        return STORIES_ROWS_COUNT + pagedListCount + progressBarCount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && shouldShowProgressIcon()) {
            // If last view and should show network state view
            return TYPE_PROGRESS
        }
        return when (position) {
            0 -> TYPE_PUBLIC_STATUS
            else -> TYPE_POST
        }
    }

    public fun setNetworkState(newNetworkState: NetworkState) {
        previousNetworkState = currentNetworkState
        currentNetworkState = newNetworkState

        updateProgressIcon(newNetworkState)
    }

    private fun updateProgressIcon(newNetworkState: NetworkState) {
        val lastItemIndex = itemCount - 1
        if (shouldShowProgressIcon()) {
            if (!isProgressIconShown()) {
                notifyItemInserted(lastItemIndex + 1)
            } else if (previousNetworkState !== newNetworkState) {
                notifyItemChanged(lastItemIndex)
            }
        } else if (isProgressIconShown()) {
            notifyItemRemoved(lastItemIndex)
        }
    }

    private fun shouldShowProgressIcon() = currentNetworkState != NetworkState.LOADED

    private fun isProgressIconShown() = previousNetworkState != NetworkState.LOADED

    companion object{

        private const val TYPE_PUBLIC_STATUS = R.layout.stories_view
        private const val TYPE_POST = R.layout.tv_show_episode_item
        private const val TYPE_PROGRESS = R.layout.progress_bar_row

        // TODO: 19-06-2020 Fix issue: On initial load, loadAfter is also called. Make this count 1
        private const val STORIES_ROWS_COUNT = 0

    }

    interface IPostClickListener {
        fun onProfileNameClicked(tvShowData: TvShowData)
    }

}