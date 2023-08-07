package com.hrudhaykanth116.instagramclone.utils.ui.list

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R

fun RecyclerView.addSpacing(
    orientation: Int = DividerItemDecoration.VERTICAL // Most common orientation. Can override
){
    addItemDecoration(DividerItemDecoration(context, orientation).apply {
        val drawable = when (orientation) {
            DividerItemDecoration.HORIZONTAL -> R.drawable.divider_item_horizontal
            else -> R.drawable.divider_item_vertical
        }
        setDrawable(ContextCompat.getDrawable(context, drawable)!!)
    })
}