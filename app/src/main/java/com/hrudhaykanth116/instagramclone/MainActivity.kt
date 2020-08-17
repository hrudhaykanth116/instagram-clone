package com.hrudhaykanth116.instagramclone

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hrudhaykanth116.instagramclone.fcm.FirebaseTokenGenerator
import com.hrudhaykanth116.instagramclone.network.RetroApiClient
import com.hrudhaykanth116.instagramclone.network.RetroApis
import com.hrudhaykanth116.instagramclone.notifications.NotificationsChannelsManager
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {


    public lateinit var apisClient: RetroApis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initRetrofit()

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        setUpBottomNavigationView(navController)

        FirebaseTokenGenerator().generateToken()
        NotificationsChannelsManager().createDefaultNotificationChannel(applicationContext)

    }

    private fun initRetrofit() {
        apisClient = RetroApiClient.getRetroApiService()
    }

    private fun setUpBottomNavigationView(navController: NavController) {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            onBottomMenuItemSelected(menuItem, navController)
        }
        bottomNavigationView.setOnNavigationItemReselectedListener {
            // Do nothing when menu item reselected.
        }
        bottomNavigationView.itemIconTintList = null
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
