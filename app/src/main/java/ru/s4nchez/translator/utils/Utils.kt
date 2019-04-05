package ru.s4nchez.translator.utils

import android.content.Context
import android.net.ConnectivityManager

fun isInternetConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo ?: return false
    return activeNetwork.isConnected
}