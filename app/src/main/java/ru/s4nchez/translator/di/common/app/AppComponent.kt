package ru.s4nchez.translator.di.common.app

import dagger.Component
import ru.s4nchez.translator.di.common.network.NetworkModule
import ru.s4nchez.translator.di.translator.TranslatorComponent
import ru.s4nchez.translator.di.translator.TranslatorModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun plusTranslatorComponent(module: TranslatorModule): TranslatorComponent
}

// TODO: Поправить di, вынести объекты, относящиеся к логики переводчика отдельно от презентора