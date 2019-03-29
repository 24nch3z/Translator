package ru.s4nchez.translator.di.common

import dagger.Component
import ru.s4nchez.translator.di.translator.TranslatorComponent
import ru.s4nchez.translator.di.translator.TranslatorModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun plusTranslatorComponent(module: TranslatorModule): TranslatorComponent
}