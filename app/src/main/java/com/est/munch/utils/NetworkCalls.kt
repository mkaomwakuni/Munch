package com.est.munch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow


class NetworkChecker: ConnectivityManager.NetworkCallback() {

    private  val isNetworkAvailable = MutableStateFlow(false)

    //Implement checking activity
    fun NetworkAvailabilityChecker(context: Context) : MutableStateFlow<Boolean> {

        val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        val network = connectivityManager.activeNetwork
        if (network==null){
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities==null){
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            else -> {
                isNetworkAvailable.value = false
                isNetworkAvailable
            }
        }
        }
}