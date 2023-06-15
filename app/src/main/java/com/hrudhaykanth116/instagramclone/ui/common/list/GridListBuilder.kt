package com.hrudhaykanth116.instagramclone.ui.common.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrudhaykanth116.instagramclone.utils.ui.list.addSpacing

fun RecyclerView.setGridLayout(
    listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
){
    val staggeredGridLayoutManager = GridLayoutManager(
        context,
        3,
        GridLayoutManager.VERTICAL,
        false
    )
    adapter = listAdapter
    layoutManager = staggeredGridLayoutManager
    addSpacing()
}