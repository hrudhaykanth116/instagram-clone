package com.hrudhaykanth116.instagramclone

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hrudhaykanth116.instagramclone.fcm.FirebaseTokenGenerator
import com.hrudhaykanth116.instagramclone.notifications.NotificationsChannelsManager
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            onBottomMenuItemSelected(menuItem, navController)
            true
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
    ) {

        val fragment = supportFragmentManager.findFragmentByTag(menuItem.itemId.toString())
        val popBackStack = navController.popBackStack(menuItem.itemId, false)
        if (!popBackStack) {
            navController.navigate(menuItem.itemId)
        }
    }

}
