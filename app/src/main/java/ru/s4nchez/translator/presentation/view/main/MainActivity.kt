package ru.s4nchez.translator.presentation.view.main

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.s4nchez.translator.R
import ru.s4nchez.translator.presentation.view.common.BaseActivity
import ru.s4nchez.translator.presentation.view.common.NetworkStatusChangeListener
import ru.s4nchez.translator.presentation.view.translator.TranslatorFragment

class MainActivity : BaseActivity(), NetworkStatusChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TranslatorFragment())
                    .commit()
        }
    }

    override fun networkStatusChange(isInternetConnected: Boolean) {
        internet_disable.visibility = if (isInternetConnected) View.GONE else View.VISIBLE

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is NetworkStatusChangeListener) {
            fragment.networkStatusChange(isInternetConnected)
        }
    }
}
