package com.hrudhaykanth116.instagramclone.ui.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.data.models.MovieData
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.ui.viewholders.PublicStoriesViewHolder
import com.hrudhaykanth116.instagramclone.ui.viewholders.TvShowEpisodeItemViewHolder
import com.hrudhaykanth116.instagramclone.ui.viewholders.ViewHoldersCreator

class HomeFragmentAdapter(
    private val postClickListener: IPostClickListener
) :
    PagingDataAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {

    private val movieDataList: ArrayList<MovieData> = ArrayList()

    public fun updateMovieDataList(movieDataList: List<MovieData>) {
        this.movieDataList.clear()
        this.movieDataList.addAll(movieDataList)
        // Notify first item(public story) changed
//        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHoldersCreator.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is PublicStoriesViewHolder -> {
                val publicStoryViewHolder = viewHolder as PublicStoriesViewHolder
                publicStoryViewHolder.bind(movieDataList)
            }
            is TvShowEpisodeItemViewHolder -> {
                val tvShowData: TvShowData = getItem(position - 1) as TvShowData
                val postViewHolder = viewHolder as TvShowEpisodeItemViewHolder
                postViewHolder.bind(tvShowData, postClickListener)
            }
            else -> {
//                throw Exception("Wrong view type: ${viewHolder.itemViewType}")
                Log.e(TAG, "onBindViewHolder: itemViewType: ${viewHolder.itemViewType}")
            }
        }

    }

    override fun getItemCount(): Int {
        val pagedListCount = super.getItemCount()
        return STORIES_ROWS_COUNT + pagedListCount
    }

    override fun getItemViewType(position: Int): Int {
        val itemViewType = when (position) {
            0 -> TYPE_PUBLIC_STORIES
            else -> TYPE_POST
        }
        return itemViewType
    }

    companion object {

        private const val TAG = "HomeFragmentAdapter"

        const val TYPE_PUBLIC_STORIES = R.layout.stories_view
        private const val TYPE_POST = R.layout.tv_show_episode_item

        // TODO: 19-06-2020 Fix issue: On initial load, loadAfter is also called. Make this count 1
        private const val STORIES_ROWS_COUNT = 0

    }

    interface IPostClickListener {
        fun onProfileNameClicked(tvShowData: TvShowData)
    }

}