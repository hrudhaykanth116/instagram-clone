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
import com.hrudhaykanth116.instagramclone.models.MovieData
import com.hrudhaykanth116.instagramclone.models.NetworkState
import kotlinx.android.synthetic.main.main_post_item.view.*
import kotlinx.android.synthetic.main.main_status_layout.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*


class MainPostsAdapter : PagedListAdapter<MovieData, RecyclerView.ViewHolder>(MovieData.diffUtillCallback) {

    private val moviesDataList: ArrayList<MovieData> = ArrayList()
    private val TYPE_PUBLIC_STATUS = 1
    private val TYPE_POST = 2
    private val TYPE_PROGRESS = 3

    private var networkState: NetworkState = NetworkState.LOADED

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

    class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_status_layout, parent, false)
                viewHolder = PublicStatusViewHolder(itemView)
            }
            TYPE_POST -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_post_item, parent, false)
                viewHolder = PostViewHolder(itemView)
            }
            TYPE_PROGRESS -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.progress_bar_row, parent, false)
                viewHolder = ProgressHolder(itemView)
            }
            else -> {
                throw Exception("Wrong view type")
            }
        }

        return viewHolder
    }

    public fun addPostsAtStart(newList: List<MovieData>){
        moviesDataList.addAll(0, newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        val pagedListCount = super.getItemCount()
        return pagedListCount   // user posts + public status
    }

    override fun getItemViewType(position: Int): Int {
//        if(hasExtraRow() && position == itemCount - 1){
//            return TYPE_PROGRESS
//        }
//        return when(position) {
//            0 -> TYPE_POST
//            else -> TYPE_POST
//        }
        return TYPE_POST
    }

    public fun setNetworkState(newNetworkState: NetworkState){
//        val previousState = networkState
//        val previousExtraRow = hasExtraRow()
//        networkState = newNetworkState
//        val newExtraRow = hasExtraRow()
//        if (previousExtraRow != newExtraRow) {
//            if (previousExtraRow) {
//                notifyItemRemoved(itemCount)
//            } else {
//                notifyItemInserted(itemCount)
//            }
//        } else if (newExtraRow && previousState !== newNetworkState) {
//            notifyItemChanged(itemCount - 1)
//        }
    }

    private fun hasExtraRow() = networkState != NetworkState.LOADED

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder.itemViewType) {
            TYPE_PUBLIC_STATUS -> {
                PublicStatusViewFiller().fillPublicStatusView(viewHolder as PublicStatusViewHolder)
            }
            TYPE_POST -> {
                val movieData = getItem(position) as MovieData
                UserPostViewFiller().fillPostView(viewHolder as PostViewHolder, movieData)
            }
            TYPE_PROGRESS -> {
                // TODO: 07-06-2020  Set progress bar
            }
            else -> {
                throw Exception("Wrong view type")
            }
        }

    }

    override fun submitList(pagedList: PagedList<MovieData>?) {
        super.submitList(pagedList)
    }
}