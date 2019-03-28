package ru.s4nchez.translator.di.translator

import dagger.Module
import dagger.Provides
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter

@Module
class TranslatorModule {

    @Provides
    fun provideTranslatorPresenter(): TranslatorPresenter {
        return TranslatorPresenter()
    }
}