package ru.s4nchez.translator.di.common

import android.content.Context
import ru.s4nchez.translator.di.common.app.AppComponent
import ru.s4nchez.translator.di.common.app.AppModule
import ru.s4nchez.translator.di.common.app.DaggerAppComponent
import ru.s4nchez.translator.di.screens.ScreensComponent
import ru.s4nchez.translator.di.screens.ScreensModule

class ComponentManager(private val appContext: Context) {

    private val appComponent: AppComponent = buildAppComponent()

    private var screensComponent: ScreensComponent? = null

    fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(appContext))
                .build()
    }

    fun buildTranslatorComponent(): ScreensComponent =
            screensComponent ?: appComponent.plusTranslatorComponent(ScreensModule())
                    .also { screensComponent = it }

    fun destroyTranslatorComponent() {
        screensComponent = null
    }
}