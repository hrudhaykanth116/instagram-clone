package com.hrudhaykanth116.instagramclone.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.databinding.ItemSearchResultBinding
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader

class SearchItemListViewHolder(
    private val binding: ItemSearchResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        tvShowData: TvShowData,
        onItemClick: (tvShowData: TvShowData) -> Unit
    ){
        binding.title.text = tvShowData.name
        ImageLoader.load(
            MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath,
            binding.image.innerImg
        )
        binding.description.text = tvShowData.overview

        binding.root.setOnClickListener {
            onItemClick(tvShowData)
        }
    }

}