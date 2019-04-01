package ru.s4nchez.translator.di.translator

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.s4nchez.translator.data.translator.datasource.MemoryTranslationDataSource
import ru.s4nchez.translator.data.translator.datasource.NetworkTranslationDataSource
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.repository.TranslationRepository
import ru.s4nchez.translator.data.translator.repository.TranslationRepositoryImpl
import ru.s4nchez.translator.di.common.scope.PerScreenScope
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractorImpl
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter
import javax.inject.Named

@Module
class TranslatorModule {

    companion object {
        private const val NETWORK_DATA_SOURCE = "NETWORK_DATA_SOURCE"
        private const val MEMORY_DATA_SOURCE = "MEMORY_DATA_SOURCE"
    }

    @Provides
    @PerScreenScope
    @Named(NETWORK_DATA_SOURCE)
    fun provideNetworkTranslationDataSource(retrofit: Retrofit): TranslationDataSource {
        return NetworkTranslationDataSource(retrofit)
    }

    @Provides
    @PerScreenScope
    @Named(MEMORY_DATA_SOURCE)
    fun provideMemoryTranslationDataSource(): TranslationDataSource {
        return MemoryTranslationDataSource()
    }

    @Provides
    @PerScreenScope
    fun provideTranslationRepository(
        @Named(NETWORK_DATA_SOURCE) networkTranslationDataSource: TranslationDataSource,
        @Named(MEMORY_DATA_SOURCE) memoryTranslationDataSource: TranslationDataSource
    ): TranslationRepository {

        return TranslationRepositoryImpl(networkTranslationDataSource, memoryTranslationDataSource)
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