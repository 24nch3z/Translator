package ru.s4nchez.sharedprefhelper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("ApplySharedPref")
class SharedPrefHelper(context: Context) {

    private val sharedPref: SharedPreferences
    private val filename = "shared"

    init {
        sharedPref = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    }

    fun <T> save(key: String, value: T) {
        val editor = sharedPref.edit()
        when (value) {
            is Long -> editor.putLong(key, value)
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Double -> editor.putString(key, value.toString())
        }
        editor.commit()
    }

    fun getLong(key: String, defaultValue: Long) = sharedPref.getLong(key, defaultValue)

    fun getInt(key: String, defaultValue: Int) = sharedPref.getInt(key, defaultValue)

    fun getString(key: String, defaultValue: String) = sharedPref.getString(key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean) = sharedPref.getBoolean(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float) = sharedPref.getFloat(key, defaultValue)

    fun getDouble(key: String, defaultValue: Double) = sharedPref.getString(key, defaultValue.toString()).toDouble()

    fun contains(key: String) = sharedPref.contains(key)

    fun remove(key: String) {
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.commit()
    }
}