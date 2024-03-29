package com.hrudhaykanth116.instagramclone.ui.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hrudhaykanth116.instagramclone.databinding.ActivitySplashBinding
import com.hrudhaykanth116.instagramclone.ui.screens.signin.SignInActivity

class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Can be triggered for api calls to manage the first screen to be shown
        Handler().postDelayed(Runnable {
            validateUserAndNavigate()
        }, SPLASH_DISPLAY_LENGTH)

    }

    private fun validateUserAndNavigate() {
        val firebaseAuth = Firebase.auth
        if (firebaseAuth.currentUser != null) {
            navigateToMainActivity()
        } else {
            navigateToSignInActivity()
        }
    }

    private fun navigateToMainActivity() {
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

    private fun navigateToSignInActivity() {
        val mainActivityIntent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

    companion object{
        private const val SPLASH_DISPLAY_LENGTH: Long = 0

    }

}