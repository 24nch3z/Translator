package ru.s4nchez.logger

import android.util.Log

/*
    В Application прописать:
    if (!BuildConfig.DEBUG) {
        Logger.setEnabled(false)
    }
 */
object Logger {

    private var TAG = "sssss"
    private var isEnabled = true

    fun setTag(tag: String) {
        TAG = tag
    }

    fun setEnabled(flag: Boolean) {
        isEnabled = flag
    }

    fun d(vararg args: Any?) {
        if (!isEnabled) return
        val argsStr = StringBuilder()
        args.map { it.toString() }.forEach { argsStr.append(it).append(" ") }
        Log.d(TAG, argsStr.toString().trim())
    }
}