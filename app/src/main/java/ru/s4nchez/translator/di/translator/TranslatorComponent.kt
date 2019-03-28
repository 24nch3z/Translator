package ru.s4nchez.translator.di.translator

import dagger.Subcomponent
import ru.s4nchez.translator.di.common.scope.PerScreenScope
import ru.s4nchez.translator.presentation.view.translator.TranslatorFragment

@PerScreenScope
@Subcomponent(modules = [TranslatorModule::class])
interface TranslatorComponent {
    fun inject(view: TranslatorFragment)
}