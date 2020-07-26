package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.confidential.MoviesDbConstants
import com.hrudhaykanth116.instagramclone.models.TvShowData
import com.hrudhaykanth116.instagramclone.ui.home.HomeFragmentDirections
import com.hrudhaykanth116.instagramclone.ui.search.SearchFragment
import com.hrudhaykanth116.instagramclone.ui.search.SearchFragmentDirections

class SearchResultsAdapter() :
    PagedListAdapter<TvShowData, RecyclerView.ViewHolder>(TvShowData.diffUtillCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)

        return SearchResultsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val tvShowData: TvShowData = getItem(position) as TvShowData
        (holder as SearchResultsViewHolder).bind(tvShowData)

    }


    class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val searchResultImg = itemView as ImageView

        public fun bind(tvShowData: TvShowData) {
            // Sample data
            // val imgUrl = "https://picsum.photos/id/${holder.adapterPosition * 8}/300"
            val imgUrl = MoviesDbConstants.IMAGES_BASE_URL + tvShowData.posterPath
            Glide
                .with(searchResultImg)
                .load(imgUrl)
                .into(searchResultImg)

            searchResultImg.setOnClickListener {
                val tvShowFragmentAction = SearchFragmentDirections.tvShowFragmentAction(tvShowData)
                itemView.findNavController().navigate(tvShowFragmentAction)
            }

        }

    }

}