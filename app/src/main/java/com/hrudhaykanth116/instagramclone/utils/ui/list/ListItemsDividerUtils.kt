package com.hrudhaykanth116.instagramclone.utils.ui.list

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.R

fun RecyclerView.addSpacing(
    orientation: Int = DividerItemDecoration.VERTICAL // Most common orientation. Can override
){
    addItemDecoration(DividerItemDecoration(context, orientation).apply {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_space_grid)!!)
    })
}