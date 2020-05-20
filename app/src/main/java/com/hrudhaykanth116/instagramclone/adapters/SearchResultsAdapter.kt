package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R

class SearchResultsAdapter(private val list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_result_image, parent, false)

        return SearchResultsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultImg = holder.itemView as ImageView

        val searchImgUrl = "https://picsum.photos/id/${holder.adapterPosition * 8}/300"
        Glide
            .with(searchResultImg)
            .load(searchImgUrl)
            .into(searchResultImg)

    }

    class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}