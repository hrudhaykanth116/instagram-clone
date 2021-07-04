package com.hrudhaykanth116.instagramclone.ui.screens

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.databinding.MainActivityBinding
import com.hrudhaykanth116.instagramclone.fcm.FirebaseTokenGenerator
import com.hrudhaykanth116.instagramclone.notifications.NotificationsChannelsManager
import com.hrudhaykanth116.instagramclone.ui.screens.base.BaseActivity
import com.hrudhaykanth116.instagramclone.utils.extensions.navigation.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpBottomNavigationView()

        FirebaseTokenGenerator().generateToken()
        NotificationsChannelsManager().createDefaultNotificationChannel(applicationContext)

    }

//    override fun onSupportNavigateUp(): Boolean {
//        return currentNavController?.value?.navigateUp() ?: false
//    }

    private fun setUpBottomNavigationView(navController: NavController? = null) {

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        val navGraphIds = listOf(
            R.navigation.home_navigation_graph,
            R.navigation.search_navigation_graph,
            R.navigation.post_navigation_graph,
            R.navigation.activity_navigation_graph,
            R.navigation.profile_navigation_graph,
        )

        bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        ).observe(this){
            // Sets up action bar with navigation controller to handle navigation icon.
            // setupActionBarWithNavController(it)
        }
        bottomNavigationView.itemIconTintList = null
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
