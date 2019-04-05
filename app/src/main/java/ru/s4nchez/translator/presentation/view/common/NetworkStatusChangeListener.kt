package ru.s4nchez.translator.presentation.view.common

interface NetworkStatusChangeListener {
    fun networkStatusChange(isInternetConnected: Boolean)
}