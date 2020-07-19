package com.hrudhaykanth116.instagramclone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.SearchResultsAdapter
import com.hrudhaykanth116.instagramclone.models.TvShowData
import kotlinx.android.synthetic.main.tv_show_fragment.*
import kotlinx.android.synthetic.main.tv_show_fragment.view.*

class TvShowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_show_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvShowBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val tvShowData = arguments?.let {
            TvShowFragmentArgs.fromBundle(it).tvShowData
        }
        fillView(view, tvShowData)

    }

    private fun fillView(
        view: View,
        tvShowData: TvShowData?
    ) {
        tvShowName.text = tvShowData?.name ?: "Tv show name"

        val testList = ArrayList<String>()
        for (i in 1..60) {
            testList.add("Item: $i")
        }

        view.tvShowImages.adapter = SearchResultsAdapter(testList)
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        view.tvShowImages.layoutManager = gridLayoutManager
    }

}