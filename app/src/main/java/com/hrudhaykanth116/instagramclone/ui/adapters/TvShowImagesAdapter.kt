package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.databinding.ImageViewBinding
import com.hrudhaykanth116.instagramclone.utils.image.ImageLoader

class TvShowImagesAdapter(
    private val mImagesList: ArrayList<String?> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun updateList(imagesList: List<String?>){
        mImagesList.clear()
        mImagesList.addAll(imagesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TvShowImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mImagesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val imgUrl = mImagesList[position]
        (holder as TvShowImageViewHolder).bind(imgUrl)

    }



    class TvShowImageViewHolder(private val binding: ImageViewBinding) : RecyclerView.ViewHolder(binding.root){


        public fun bind(imgUrl: String?) {
            ImageLoader.load(imgUrl, binding.mainImageView)
        }

    }

}