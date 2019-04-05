package ru.s4nchez.translator.presentation.view.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.s4nchez.translator.R
import ru.s4nchez.translator.presentation.view.translator.TranslatorFragment

class MainActivity : AppCompatActivity() {

    private val networkStatusChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            internet_disable.visibility = if (isInternetConnected()) View.GONE else View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TranslatorFragment())
                    .commit()
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

    private fun isInternetConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo ?: return false
        return activeNetwork.isConnected
    }
}
