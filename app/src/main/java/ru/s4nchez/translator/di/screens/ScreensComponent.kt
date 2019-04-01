package ru.s4nchez.translator.di.screens

import dagger.Subcomponent
import ru.s4nchez.translator.di.common.scope.PerScreenScope
import ru.s4nchez.translator.presentation.view.translator.TranslatorFragment

@PerScreenScope
@Subcomponent(modules = [ScreensModule::class])
interface ScreensComponent {
    fun inject(view: TranslatorFragment)
}