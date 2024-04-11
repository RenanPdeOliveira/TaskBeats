package com.comunidadedevspace.taskbeats.core.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.comunidadedevspace.taskbeats.core.domain.network.ConnectivityObserver

class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetwork ?: return false
        val cap = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        return when {
            cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}