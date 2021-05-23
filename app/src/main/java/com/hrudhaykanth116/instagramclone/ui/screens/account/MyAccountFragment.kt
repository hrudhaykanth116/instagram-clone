package com.hrudhaykanth116.instagramclone.ui.screens.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.databinding.MyAccountFragmentBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseFragment
import com.hrudhaykanth116.instagramclone.ui.screens.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment : BaseFragment() {

    lateinit var binding: MyAccountFragmentBinding

    companion object {
        fun newInstance() = MyAccountFragment()
    }

    private lateinit var viewModel: MyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyAccountFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOutBtn.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()
            activity?.finishAffinity()
            val signInIntent = Intent(context, SignInActivity::class.java)
            signInIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(signInIntent)
        }

    }

}
