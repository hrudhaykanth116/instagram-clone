package com.hrudhaykanth116.instagramclone.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.databinding.ProgressBarRowBinding
import com.hrudhaykanth116.instagramclone.databinding.StoriesViewBinding
import com.hrudhaykanth116.instagramclone.databinding.TvShowEpisodeItemBinding

class ViewHoldersCreator {

    companion object{
        public fun createViewHolder(parent: ViewGroup, viewId: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val viewHolder: RecyclerView.ViewHolder = when (viewId) {
                R.layout.stories_view -> {
                    PublicStoriesViewHolder(StoriesViewBinding.inflate(inflater, parent, false))
                }
                R.layout.tv_show_episode_item -> {
                    TvShowEpisodeItemViewHolder(TvShowEpisodeItemBinding.inflate(inflater, parent, false))
                }
                R.layout.progress_bar_row -> {
                    ProgressViewHolder(ProgressBarRowBinding.inflate(inflater, parent, false))
                }
                else -> {
                    throw Exception("Wrong view type")
                }
            }

            return viewHolder
        }
    }

}