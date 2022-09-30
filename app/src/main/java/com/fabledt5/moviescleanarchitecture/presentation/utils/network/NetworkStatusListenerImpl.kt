package com.fabledt5.moviescleanarchitecture.presentation.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkStatusListenerImpl @Inject constructor(context: Context) : NetworkStatusListener {

    private val cm = context.getSystemService(ConnectivityManager::class.java)
    private val coroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default)

    override val networkStatus = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                launch { send(NetworkStatus.Unavailable) }
            }

            override fun onLost(network: Network) {
                launch { send(NetworkStatus.Unavailable) }
            }

            override fun onAvailable(network: Network) {
                launch { send(NetworkStatus.Available) }
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(request, networkStatusCallback)

        if (isNetworkAvailable()) trySend(NetworkStatus.Available)
        else trySend(NetworkStatus.Unavailable)

        awaitClose {
            cm.unregisterNetworkCallback(networkStatusCallback)
        }
    }.shareIn(scope = coroutineScope, started = SharingStarted.Lazily, replay = 1)

    private fun isNetworkAvailable() = cm.run {
        getNetworkCapabilities(activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

}