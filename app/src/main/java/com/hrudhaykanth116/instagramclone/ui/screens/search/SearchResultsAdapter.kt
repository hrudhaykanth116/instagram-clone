package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.databinding.ImageViewBinding
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader

class SearchResultsAdapter(
    private val onItemClick: (itemData: TvShowData) -> Unit
) :
    PagingDataAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val tvShowData: TvShowData = getItem(position) as TvShowData
        (holder as SearchResultsViewHolder).bind(tvShowData)

    }


    inner class SearchResultsViewHolder(val binding: ImageViewBinding) : RecyclerView.ViewHolder(binding.root){

        public fun bind(tvShowData: TvShowData) {
            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
            ImageLoader.load(imgUrl, binding.mainImageView)

            binding.root.setOnClickListener {
                onItemClick(tvShowData)
            }
        }
    }
}