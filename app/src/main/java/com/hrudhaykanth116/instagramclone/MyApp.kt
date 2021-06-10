package com.hrudhaykanth116.instagramclone

import android.app.Application
import com.hrudhaykanth116.instagramclone.utils.network.ConnectionStateMonitor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ConnectionStateMonitor.enable(applicationContext, object : ConnectionStateMonitor.ConnectivityReceiverListener{
            override fun onNetworkConnectionChanged(isConnected: Boolean) {
                // isConnected state is stored in ConnectionStateMonitor
            }
        })
    }

}