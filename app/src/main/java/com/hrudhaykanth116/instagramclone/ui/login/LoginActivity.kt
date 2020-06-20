package com.hrudhaykanth116.instagramclone.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = Firebase.auth

        signInBtn.setOnClickListener {
            signInWithEmailPassword(emailTextField.text.toString(), passwordTextField.text.toString())
        }

    }

    override fun onStart() {
        super.onStart()
        updateCurrentUser()
    }

    override fun onDestroy() {
        firebaseAuth.signOut()
        super.onDestroy()
    }

    private fun signInWithEmailPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmailPassword: Authentication successful.")
                updateCurrentUser()
            }else{
                Log.e(TAG, "signInWithEmailPassword: Authentication failure: ${task.exception}")
            }
        }
    }

    private fun updateCurrentUser() {
        val currentUser = firebaseAuth.currentUser
        Log.d(TAG, "onStart: currentUser: ${currentUser?.email}")
    }

    companion object{
        private val TAG = LoginActivity.toString()
    }

}
