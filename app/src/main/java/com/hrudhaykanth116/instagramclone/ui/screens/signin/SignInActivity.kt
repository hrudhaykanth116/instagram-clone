package com.hrudhaykanth116.instagramclone.ui.screens.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.ui.screens.MainActivity
import com.hrudhaykanth116.instagramclone.databinding.SignInActivityBinding
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val binding: SignInActivityBinding by lazy {
        SignInActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signInBtn.setOnClickListener {
            signInWithEmailPassword(
                binding.emailTextField.text.toString(),
                binding.passwordTextField.text.toString()
            )
        }

    }

    private fun signInWithEmailPassword(
        email: String,
        password: String
    ) {
        binding.signInBtn.visibility = View.INVISIBLE
        binding.signInProgressBar.visibility = View.VISIBLE

        val firebaseAuth = Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmailPassword: Authentication successful.")
                navigateToMainActivity()
            }else{
                Log.e(TAG, "signInWithEmailPassword: Authentication failure: ${task.exception}")
                Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                binding.signInBtn.visibility = View.VISIBLE
                binding.signInProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun navigateToMainActivity() {
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainActivityIntent)
    }

    companion object{
        private val TAG = SignInActivity.toString()
    }

}
