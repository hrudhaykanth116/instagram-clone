package com.hrudhaykanth116.instagramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.databinding.ItemPagingLoadStateFooterBinding

class PagingLoadStateViewHolder(
    private val binding: ItemPagingLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = false
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PagingLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_paging_load_state_footer, parent, false)
            val binding = ItemPagingLoadStateFooterBinding.bind(view)
            return PagingLoadStateViewHolder(binding, retry)
        }
    }
}
