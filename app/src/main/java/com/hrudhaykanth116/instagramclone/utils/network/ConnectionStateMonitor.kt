package com.hrudhaykanth116.instagramclone.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object ConnectionStateMonitor : ConnectivityManager.NetworkCallback() {
    private var appContext: Context? = null
    private var connectivityReceiverListener: ConnectivityReceiverListener? = null
    private var wasOnLostCalled = false
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NET_CAPABILITY_INTERNET)
        .addTransportType(TRANSPORT_WIFI)
        .addTransportType(TRANSPORT_CELLULAR)
        .apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                addCapability(NET_CAPABILITY_VALIDATED)
        }
        .build()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val connectivityManager: ConnectivityManager? by lazy {
        appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }
    fun enable(
        context: Context,
        connectivityReceiverListener: ConnectivityReceiverListener
    ) {
        this.connectivityReceiverListener = connectivityReceiverListener
        this.appContext = context.applicationContext
        checkNetworkConnection()
        if (!isRunning) {
            isRunning = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager?.registerDefaultNetworkCallback(this)
            } else {
                connectivityManager?.registerNetworkCallback(networkRequest, this)
            }
        }
    }
    private fun checkNetworkConnection() {
        ioScope.launch {
            isConnected = isOnline()
            connectivityReceiverListener?.onNetworkConnectionChanged(isConnected)
        }
    }
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d(TAG, "onCapabilitiesChanged: ")
        if (networkCapabilities.hasTransport(TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(TRANSPORT_CELLULAR)
        ) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
                ) {
                    onConnected()
                } else {
                    onDisconnected()
                }
            } else {
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)) {
                    onConnected()
                } else {
                    onDisconnected()
                }
            }
        } else {
            onDisconnected()
        }
    }
    private fun onConnected() {
        isConnected = true
        connectivityReceiverListener?.onNetworkConnectionChanged(true)
    }
    private fun onDisconnected() {
        isConnected = false
        connectivityReceiverListener?.onNetworkConnectionChanged(false)
    }
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d(TAG, "onAvailable")
        if (wasOnLostCalled) {
            wasOnLostCalled = false
        } else {
            onConnected()
        }
    }
    override fun onLost(network: Network) {
        super.onLost(network)
        Log.d(TAG, "onLost")
        wasOnLostCalled = true
        onDisconnected()
    }
    private fun isOnline(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAddress, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }
    fun disable() {
        Log.d(TAG, "disable: ")
        appContext?.let {
            connectivityManager?.unregisterNetworkCallback(this)
        }
        isConnected = false
        isRunning = false
        appContext = null
    }
    const val TAG = "ConnectionStateMonitor"
    var isConnected = false
        private set
    var isRunning = false
        private set
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}

@Deprecated("Use ConnectionStateMonitor instead")
object ConnectionStateMonitor2 : ConnectivityManager.NetworkCallback() {
    private var appContext: Context? = null
    private var connectivityReceiverListener: ConnectivityReceiverListener? = null
    private var wasOnLostCalled = false
    private var disconnectionNotificationTimer = object : CountDownTimer(3000, 3000) {
        override fun onTick(p0: Long) {
        }
        override fun onFinish() {
            if (isConnected) {
                Log.d(TAG, "network onDisconnected")
                isConnected = false
                connectivityReceiverListener?.onNetworkConnectionChanged(false)
            }
        }
    }
    private var connectionNotificationTimer = object : CountDownTimer(3000, 3000) {
        override fun onTick(p0: Long) {
        }
        override fun onFinish() {
            ioScope.launch {
                if (!isConnected && isOnline()) {
                    Log.d(TAG, "network onConnected")
                    isConnected = true
                    connectivityReceiverListener?.onNetworkConnectionChanged(true)
                }
            }
        }
    }
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NET_CAPABILITY_INTERNET)
        .addTransportType(TRANSPORT_WIFI)
        .addTransportType(TRANSPORT_CELLULAR)
        .apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                addCapability(NET_CAPABILITY_VALIDATED)
        }
        .build()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val connectivityManager: ConnectivityManager? by lazy {
        appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }
    fun enable(
        context: Context,
        connectivityReceiverListener: ConnectivityReceiverListener
    ) {
        this.connectivityReceiverListener = connectivityReceiverListener
        this.appContext = context.applicationContext
        checkNetworkConnection()
        if (!isRunning) {
            isRunning = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager?.registerDefaultNetworkCallback(this)
            } else {
                connectivityManager?.registerNetworkCallback(networkRequest, this)
            }
        }
    }
    private fun checkNetworkConnection() {
        ioScope.launch {
            isConnected = isOnline()
            connectivityReceiverListener?.onNetworkConnectionChanged(isConnected)
        }
    }
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d(TAG, "onCapabilitiesChanged: ")
        if (networkCapabilities.hasTransport(TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(TRANSPORT_CELLULAR)
        ) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
                ) {
                    onConnected()
                } else {
                    onDisconnected()
                }
            } else {
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)) {
                    onConnected()
                } else {
                    onDisconnected()
                }
            }
        } else {
            onDisconnected()
        }
    }
    private fun onConnected() {
        disconnectionNotificationTimer.cancel()
        connectionNotificationTimer.start()
    }
    private fun onDisconnected() {
        connectionNotificationTimer.cancel()
        disconnectionNotificationTimer.start()
    }
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d(TAG, "onAvailable")
        if (wasOnLostCalled) {
            wasOnLostCalled = false
        } else {
            onConnected()
        }
    }
    override fun onLost(network: Network) {
        super.onLost(network)
        Log.e(TAG, "onLost")
        wasOnLostCalled = true
        onDisconnected()
    }
    private fun isOnline(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAddress, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }
    fun disable() {
        Log.d(TAG, "disable: ")
        appContext?.let {
            connectivityManager?.unregisterNetworkCallback(this)
        }
        isConnected = false
        isRunning = false
        appContext = null
    }
    const val TAG = "ConnectionStateMonitor"
    var isConnected = false
        private set
    var isRunning = false
        private set
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}