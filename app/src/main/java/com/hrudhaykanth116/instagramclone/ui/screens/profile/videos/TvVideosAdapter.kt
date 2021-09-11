package com.hrudhaykanth116.instagramclone.ui.screens.profile.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.data.models.GetTvVideosResponse
import com.hrudhaykanth116.instagramclone.databinding.ItemTvVideoBinding

class TvVideosAdapter(
    private val lifecycle: Lifecycle,
) : RecyclerView.Adapter<TvVideosAdapter.TvVideoViewHolder>() {

    private val mVideosObjList: ArrayList<GetTvVideosResponse.Result?> = ArrayList()

    fun updateVideoIds(videosObjList: List<GetTvVideosResponse.Result?>) {
        mVideosObjList.clear()
        mVideosObjList.addAll(videosObjList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvVideoViewHolder {
        val binding = ItemTvVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // TODO: 12/09/21 handle this
        lifecycle.addObserver(binding.myYoutubePlayer.videoPlayer())

        return TvVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvVideoViewHolder, position: Int) {
        holder.bind(mVideosObjList[position])
    }

    override fun getItemCount(): Int {
        return mVideosObjList.size
    }


    class TvVideoViewHolder(private val binding: ItemTvVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoObj: GetTvVideosResponse.Result?) {

            binding.textVideoTitle.text = "${videoObj?.type}"
            if (videoObj?.key != null) {
                binding.myYoutubePlayer.setVideoKey(videoObj.key)
            }else{
                // TODO: 12/09/21 handle empty case
            }
        }
    }
}