package com.hrudhaykanth116.instagramclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrudhaykanth116.instagramclone.R
import kotlinx.android.synthetic.main.main_post_item.view.*
import kotlinx.android.synthetic.main.main_status_layout.view.*
import kotlinx.android.synthetic.main.rounded_image_container.view.*


class MainPostsAdapter(private val list: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_PUBLIC_STATUS = 1
    private val TYPE_POST = 2

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postDp: ImageView = itemView.dpView.innerImg as ImageView
        val postContent: ImageView = itemView.postContent as ImageView
    }

    class PublicStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val publicStoriesContainer = itemView.public_stories_rv
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = getViewHolder(parent, viewType)
        return viewHolder
    }

    private fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_PUBLIC_STATUS -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_status_layout, parent, false)
                viewHolder = PublicStatusViewHolder(itemView)
            }
            TYPE_POST -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_post_item, parent, false)
                viewHolder = PostViewHolder(itemView)
            }else -> {
                throw Exception("Wrong view type")
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TYPE_PUBLIC_STATUS
            else -> TYPE_POST
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        
        when (viewHolder.itemViewType) {
            TYPE_PUBLIC_STATUS -> {
                val publicStatusViewHolder = viewHolder as PublicStatusViewHolder
                val testList = ArrayList<String>()
                for (i in 1..20) {
                    testList.add("Item: $i")
                }
                publicStatusViewHolder.publicStoriesContainer.adapter = PublicStoryThumbnailAdapter(testList)
//                val myStatusUrl = "https://picsum.photos/id/1035/300"
//                Glide
//                    .with(publicStatusViewHolder.itemView)
//                    .load(myStatusUrl)
//                    .into(publicStatusViewHolder.myStatus)
            }
            TYPE_POST -> {
                val postViewHolder = viewHolder as PostViewHolder
                postViewHolder.postDp.setImageResource(R.drawable.r6)
                val postImgUrl = "https://picsum.photos/id/${postViewHolder.adapterPosition * 5}/300"
                val postDpUrl = "https://picsum.photos/id/${postViewHolder.adapterPosition * 10}/300"

                Glide
                    .with(postViewHolder.itemView)
                    .load(postImgUrl)
                    .into(postViewHolder.postContent)

                Glide
                    .with(postViewHolder.itemView)
                    .load(postDpUrl)
                    .into(postViewHolder.postDp)
            }
            else -> {
                throw Exception("Wrong view type")
            }

        }



    }

}