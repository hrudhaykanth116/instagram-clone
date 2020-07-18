package com.hrudhaykanth116.instagramclone

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hrudhaykanth116.instagramclone.fcm.FirebaseTokenGenerator
import com.hrudhaykanth116.instagramclone.notifications.NotificationsChannelsManager
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            onBottomMenuItemSelected(menuItem, navController)
        }
        bottomNavigationView.setOnNavigationItemReselectedListener {
            // Do nothing when menu item reselected.
        }
        bottomNavigationView.itemIconTintList = null

        FirebaseTokenGenerator().generateToken()
        NotificationsChannelsManager().createDefaultNotificationChannel(applicationContext)

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Back button disabled until further development", Toast.LENGTH_SHORT).show()
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
