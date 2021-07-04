package com.hrudhaykanth116.instagramclone.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hrudhaykanth116.instagramclone.data.models.TvShowData
import com.hrudhaykanth116.instagramclone.databinding.ItemSearchResultBinding

class SearchListAdapter constructor(
    private val onItemClick: (itemData: TvShowData) -> Unit,
) : ListAdapter<TvShowData, SearchItemListViewHolder>(
    SearchResultDiffUtilCallback()
){

//    private val list: List<TvShowData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemListViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: SearchItemListViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick)
    }

    /*override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val resultList = ArrayList<TvShowData>()

                // Set proper result list

                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList: ArrayList<TvShowData> = results?.values as ArrayList<TvShowData>
                submitList(filteredList)
            }
        }
    }*/
}

class SearchResultDiffUtilCallback : DiffUtil.ItemCallback<TvShowData>() {

    override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
        return oldItem == newItem
    }
}

//data class SearchListItem(
//    private val title: String? = null,
//    private val description: String? = null,
//    private val imgUrl: String? = null,
//)