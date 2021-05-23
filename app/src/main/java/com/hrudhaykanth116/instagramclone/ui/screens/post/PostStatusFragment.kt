package com.hrudhaykanth116.instagramclone.ui.screens.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostStatusFragment : BaseFragment() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        postViewModel =
                ViewModelProvider(this).get(PostViewModel::class.java)
        val root = inflater.inflate(R.layout.post_status_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        postViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
