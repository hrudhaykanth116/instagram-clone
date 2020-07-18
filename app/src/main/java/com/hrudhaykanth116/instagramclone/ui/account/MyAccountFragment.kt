package com.hrudhaykanth116.instagramclone.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.my_account_fragment.*

class MyAccountFragment : Fragment() {

    companion object {
        fun newInstance() = MyAccountFragment()
    }

    private lateinit var viewModel: MyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_account_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signOutBtn.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()
            activity?.finishAffinity()
            val signInIntent = Intent(context, SignInActivity::class.java)
            signInIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(signInIntent)
        }

    }

}
