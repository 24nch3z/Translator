package ru.s4nchez.translator.di.common.app

import dagger.Component
import ru.s4nchez.translator.di.common.network.NetworkModule
import ru.s4nchez.translator.di.screens.ScreensComponent
import ru.s4nchez.translator.di.screens.ScreensModule
import ru.s4nchez.translator.di.settings.SettingsModule
import ru.s4nchez.translator.di.translator.TranslatorModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, SettingsModule::class, TranslatorModule::class])
interface AppComponent {
    fun plusTranslatorComponent(module: ScreensModule): ScreensComponent
}