package com.est.munchy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class NetworkChecker @Inject constructor(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {

    private val _isNetworkAvailable = MutableStateFlow(false)

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        // Check initial state safely
        val network = connectivityManager.activeNetwork
        val networkCapabilities = network?.let { connectivityManager.getNetworkCapabilities(it) }
        _isNetworkAvailable.value = hasInternetCapability(networkCapabilities)
    }

    private fun hasInternetCapability(networkCapabilities: NetworkCapabilities?): Boolean {
        return networkCapabilities?.let { capabilities ->
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } == true
    }

    override fun onAvailable(network: Network) {
        _isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        _isNetworkAvailable.value = false
    }

    override fun onUnavailable() {
        _isNetworkAvailable.value = false
    }

    fun getNetworkAvailability() = _isNetworkAvailable
}