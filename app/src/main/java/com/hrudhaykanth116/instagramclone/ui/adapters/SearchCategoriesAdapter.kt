package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R

class SearchCategoriesAdapter(private val list: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_category_item, parent, false)

        return SearchCategoriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultImg = holder.itemView

    }

    class SearchCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}