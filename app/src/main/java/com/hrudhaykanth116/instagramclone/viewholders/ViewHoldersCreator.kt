package com.hrudhaykanth116.instagramclone.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R

class ViewHoldersCreator {

    companion object{
        public fun createViewHolder(parent: ViewGroup, viewId: Int): RecyclerView.ViewHolder {
            val viewHolder: RecyclerView.ViewHolder
            val itemView: View = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
            viewHolder = when (viewId) {
                R.layout.stories_view -> {
                    PublicStoriesViewHolder(itemView)
                }
                R.layout.tv_show_episode_item -> {
                    TvShowEpisodeItemViewHolder(itemView)
                }
                R.layout.progress_bar_row -> {
                    ProgressViewHolder(itemView)
                }
                else -> {
                    throw Exception("Wrong view type")
                }
            }

            return viewHolder
        }
    }

}