package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.data.models.genres.Genre
import com.hrudhaykanth116.instagramclone.databinding.SearchCategoryItemBinding
import com.hrudhaykanth116.instagramclone.utils.extensions.livedata.addAll
import com.hrudhaykanth116.instagramclone.utils.extensions.livedata.clear
import com.hrudhaykanth116.instagramclone.utils.extensions.livedata.notifyObserver

class SearchCategoriesAdapter(
    private val genreList: List<Genre>,
) : RecyclerView.Adapter<SearchCategoriesAdapter.SearchCategoriesViewHolder>() {

    val mSelectedCategories: MutableLiveData<ArrayList<Genre>?> =
        MutableLiveData<ArrayList<Genre>?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCategoriesViewHolder {
        val binding =
            SearchCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: SearchCategoriesViewHolder, position: Int) {
        holder.bind(genreList[position])
    }

    fun updateSelectedCategories(selectedCategories: ArrayList<Genre>?) {
        mSelectedCategories.clear()
        if (selectedCategories != null) {
            mSelectedCategories.addAll(selectedCategories)
        }else{
            mSelectedCategories.value = null
        }
        // selectedCategories?.let {
        //     mSelectedCategories.value = selectedCategories
        // } ?: run{
        //     mSelectedCategories.value = arrayListOf()
        // }
        notifyDataSetChanged()
    }

    inner class SearchCategoriesViewHolder(val binding: SearchCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.categoryNameTV.text = genre.name

            if (mSelectedCategories.value?.contains(genre) == true) {
                binding.contentLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.selectedColor
                    )
                )
            } else {
                binding.contentLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.transparentColor
                    )
                )
            }

            binding.root.setOnClickListener {
                // Using simple straight path to update selected categories list and update the view.
                // TODO: 12/07/21 Update the view if the api is successful or something like that. 
                if (mSelectedCategories.value?.contains(genre) == true) {
                    mSelectedCategories.value?.remove(genre)
                } else {
                    // Just a normal null check
                    if (mSelectedCategories.value != null) {
                        mSelectedCategories.value!!.add(genre)
                    }else{
                        mSelectedCategories.value = arrayListOf()
                        mSelectedCategories.value!!.add(genre)
                    }
                }
                mSelectedCategories.notifyObserver()
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }
}