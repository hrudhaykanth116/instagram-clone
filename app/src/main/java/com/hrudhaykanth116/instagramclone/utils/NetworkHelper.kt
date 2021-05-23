package com.hrudhaykanth116.instagramclone.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL


class NetworkHelper {

    // User  app server address for ideal cases(app server may be down).
    private val internetCheckHost = "www.google.com"


    fun isNetworkAvailable(context: Context): Boolean{
        // Requires following permission
        // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null
    }

    fun isInternetAvailable(): Boolean{
        // Needs further investigation and verification.
        val address = InetAddress.getByName(internetCheckHost)
        return address != null
    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        // Needs investigation on executing commands.
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return if (activeNetwork != null) {
            try {
                val url = URL("http://www.google.com/")
                val urlc = url.openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 1000 // mTimeout is in seconds
                urlc.connect()
                urlc.responseCode == 200
            } catch (e: IOException) {
                Log.i("warning", "Error checking internet connection", e)
                false
            }
        } else false
    }

}