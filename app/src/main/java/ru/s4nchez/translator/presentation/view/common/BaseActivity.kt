package ru.s4nchez.translator.presentation.view.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import ru.s4nchez.translator.utils.isInternetConnected

abstract class BaseActivity : AppCompatActivity() {

    private val networkStatusChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            (this@BaseActivity as NetworkStatusChangeListener).networkStatusChange(isInternetConnected(context))
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkStatusChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkStatusChangeReceiver)
    }
}