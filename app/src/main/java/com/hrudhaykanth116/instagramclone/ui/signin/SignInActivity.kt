package com.hrudhaykanth116.instagramclone.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.MainActivity
import com.hrudhaykanth116.instagramclone.R
import kotlinx.android.synthetic.main.sign_in_activity.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val firebaseAuth = Firebase.auth
        if (firebaseAuth.currentUser != null) {
            navigateToMainActivity()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        signInBtn.setOnClickListener {
            signInWithEmailPassword(
                emailTextField.text.toString(),
                passwordTextField.text.toString(),
                firebaseAuth
            )
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun signInWithEmailPassword(
        email: String,
        password: String,
        firebaseAuth: FirebaseAuth
    ) {
        signInBtn.visibility = View.INVISIBLE
        signInProgressBar.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmailPassword: Authentication successful.")
                navigateToMainActivity()
            }else{
                Log.e(TAG, "signInWithEmailPassword: Authentication failure: ${task.exception}")
                Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                signInBtn.visibility = View.VISIBLE
                signInProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun navigateToMainActivity() {
        finishAffinity()
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    companion object{
        private val TAG = SignInActivity.toString()
    }

}
