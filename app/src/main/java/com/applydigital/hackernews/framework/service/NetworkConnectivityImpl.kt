package com.applydigital.hackernews.framework.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.applydigital.hackernews.domain.service.NetworkConnectivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkConnectivityImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkConnectivity {

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
