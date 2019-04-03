package ru.s4nchez.translator.di.screens

import dagger.Module
import dagger.Provides
import ru.s4nchez.translator.di.common.scope.PerScreenScope
import ru.s4nchez.translator.domain.settings.SettingsInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translatorfacade.interactor.TranslatorFacadeInteractor
import ru.s4nchez.translator.domain.translatorfacade.interactor.TranslatorFacadeInteractorImpl
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter

@Module
class ScreensModule {

    @Provides
    @PerScreenScope
    fun provideTranslatorFacadeInteractor(
            translatorInteractor: TranslatorInteractor,
            settingsInteractor: SettingsInteractor
    ): TranslatorFacadeInteractor {

        return TranslatorFacadeInteractorImpl(translatorInteractor, settingsInteractor)
    }

    @Provides
    fun provideTranslatorPresenter(
            translatorFacadeInteractor: TranslatorFacadeInteractor
    ): TranslatorPresenter {

        return TranslatorPresenter(translatorFacadeInteractor)
    }
}