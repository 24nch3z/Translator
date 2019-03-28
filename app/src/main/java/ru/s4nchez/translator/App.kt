package ru.s4nchez.translator

import android.app.Application
import ru.s4nchez.logger.BuildConfig
import ru.s4nchez.logger.Logger
import ru.s4nchez.translator.di.common.ComponentManager

class App : Application() {

    lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Logger.setEnabled(false)
        }

        componentManager = ComponentManager(this)
        componentManager.buildAppComponent()
    }
}