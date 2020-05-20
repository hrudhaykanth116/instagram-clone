package com.hrudhaykanth116.instagramclone.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.adapters.SearchCategoriesAdapter
import com.hrudhaykanth116.instagramclone.adapters.SearchResultsAdapter
import kotlinx.android.synthetic.main.search_fragment.view.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.search_fragment, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        searchViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val testList = ArrayList<String>()
        for (i in 1..60) {
            testList.add("Item: $i")
        }
        view.search_results_container.adapter = SearchResultsAdapter(testList)
        val staggeredGridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        view.search_results_container.layoutManager = staggeredGridLayoutManager

        view.categories.adapter = SearchCategoriesAdapter(testList)
        view.categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        super.onViewCreated(view, savedInstanceState)
    }

}
