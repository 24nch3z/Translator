package ru.s4nchez.translator.di.translator

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.s4nchez.translator.data.translator.datasource.NetworkTranslationDataSource
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.repository.TranslationRepository
import ru.s4nchez.translator.data.translator.repository.TranslationRepositoryImpl
import ru.s4nchez.translator.di.common.scope.PerScreenScope
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractorImpl
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter

@Module
class TranslatorModule {

    @Provides
    @PerScreenScope
    fun provideTranslationDataSource(retrofit: Retrofit): TranslationDataSource {
        return NetworkTranslationDataSource(retrofit)
    }

    @Provides
    @PerScreenScope
    fun provideTranslationRepository(translationDataSource: TranslationDataSource): TranslationRepository {
        return TranslationRepositoryImpl(translationDataSource)
    }

    @Provides
    @PerScreenScope
    fun provideTranslatorInteractor(translationRepository: TranslationRepository): TranslatorInteractor {
        return TranslatorInteractorImpl(translationRepository)
    }

    @Provides
    fun provideTranslatorPresenter(translatorInteractor: TranslatorInteractor): TranslatorPresenter {
        return TranslatorPresenter(translatorInteractor)
    }
}