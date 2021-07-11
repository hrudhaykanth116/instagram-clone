package com.hrudhaykanth116.instagramclone.ui.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.data.models.discover.DiscoverResult
import com.hrudhaykanth116.instagramclone.databinding.ImageViewBinding
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper

class SearchResultsAdapter() :
    PagingDataAdapter<DiscoverResult, RecyclerView.ViewHolder>(DiscoverResult.diffUtilCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val tvShowData: DiscoverResult = getItem(position) as DiscoverResult
        (holder as SearchResultsViewHolder).bind(tvShowData)

    }


    class SearchResultsViewHolder(val binding: ImageViewBinding) : RecyclerView.ViewHolder(binding.root){

        public fun bind(discoverResult: DiscoverResult) {
            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + discoverResult.posterPath
            ImageLoader.load(imgUrl, binding.root)

            binding.root.setOnClickListener {
                discoverResult.id?.let {
                    val tvShowFragmentAction = SearchScreenFragmentDirections.actionTvShowFragment(it)
                    itemView.findNavController().navigate(tvShowFragmentAction)
                } ?: run{
                    ToastHelper.showErrorToast(it.context, "No tv show id")
                }
            }

        }

    }

}