package ru.s4nchez.translator.di.common

import android.content.Context
import ru.s4nchez.translator.di.translator.TranslatorComponent
import ru.s4nchez.translator.di.translator.TranslatorModule

class ComponentManager(private val appContext: Context) {

    private val appComponent: AppComponent = buildAppComponent()

    private var translatorComponent: TranslatorComponent? = null

    fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(appContext))
                .build()
    }

    fun buildTranslatorComponent(): TranslatorComponent =
            translatorComponent ?: appComponent.plusTranslatorComponent(TranslatorModule())
                    .also { translatorComponent = it }

    fun destroyTranslatorComponent() {
        translatorComponent = null
    }
}