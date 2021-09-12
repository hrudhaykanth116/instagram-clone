package com.hrudhaykanth116.instagramclone.ui.screens.profile.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.data.models.GetTvReviewsResponse.ReviewDetails
import com.hrudhaykanth116.instagramclone.databinding.ItemTvReviewBinding

class TvReviewsAdapter() :
    PagingDataAdapter<ReviewDetails, TvReviewsAdapter.SearchResultsViewHolder>(ReviewDetails.diffUtillCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {

        val binding = ItemTvReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {

        val tvShowData: ReviewDetails = getItem(position) as ReviewDetails
        holder.bind(tvShowData)

    }


    class SearchResultsViewHolder(val binding: ItemTvReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(discoverResult: ReviewDetails) {
            binding.textContent.text = discoverResult.content
        }

    }

}