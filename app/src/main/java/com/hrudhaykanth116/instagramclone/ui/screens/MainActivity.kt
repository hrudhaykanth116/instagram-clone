package com.hrudhaykanth116.instagramclone.ui.screens

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.databinding.MainActivityBinding
import com.hrudhaykanth116.instagramclone.fcm.FirebaseTokenGenerator
import com.hrudhaykanth116.instagramclone.repository.datasources.remote.RetroApis
import com.hrudhaykanth116.instagramclone.notifications.NotificationsChannelsManager
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var retroApis: RetroApis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        setUpBottomNavigationView(navController)

        FirebaseTokenGenerator().generateToken()
        NotificationsChannelsManager().createDefaultNotificationChannel(applicationContext)

    }

    private fun setUpBottomNavigationView(navController: NavController) {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            onBottomMenuItemSelected(menuItem, navController)
        }
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            // Do nothing when menu item reselected.
        }
        binding.bottomNavigationView.itemIconTintList = null
    }

    override fun onBackPressed() {
        // TODO: 29-07-2020 Handle fragment destinations change vs bottom bar view selection.
        Toast.makeText(this, "Back functionality not yet implemented", Toast.LENGTH_SHORT).show();
    }

    private fun onBottomMenuItemSelected(
        menuItem: MenuItem,
        navController: NavController
    ): Boolean {

        val popBackStack = navController.popBackStack(menuItem.itemId, false)
        if (!popBackStack) {
            navController.navigate(menuItem.itemId)
        }

        return true
    }

}
