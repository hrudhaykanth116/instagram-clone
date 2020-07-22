package com.hrudhaykanth116.instagramclone.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R

class TvShowImagesAdapter(private val imagesList: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)

        return SearchResultsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultImg = holder.itemView as ImageView

        // dummy data for testing.
        // val imgUrl = "https://picsum.photos/id/${holder.adapterPosition * 8}/300"
        val imgUrl = imagesList[position]
        Glide
            .with(searchResultImg)
            .load(imgUrl)
            .into(searchResultImg)

    }

    class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}