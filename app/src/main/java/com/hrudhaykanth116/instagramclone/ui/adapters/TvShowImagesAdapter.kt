package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader

class TvShowImagesAdapter(private val imagesList: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)

        return TvShowImageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val imgUrl = imagesList[position]
        (holder as TvShowImageViewHolder).bind(imgUrl)

    }



    class TvShowImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val searchResultImg = itemView as ImageView

        public fun bind(imgUrl: String) {
            // dummy data for testing.
            // val imgUrl = "https://picsum.photos/id/${holder.adapterPosition * 8}/300"

            ImageLoader.load(imgUrl, searchResultImg)
        }

    }

}