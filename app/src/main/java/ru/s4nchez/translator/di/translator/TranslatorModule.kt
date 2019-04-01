package ru.s4nchez.translator.di.translator

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.s4nchez.translator.data.translator.datasource.MemoryTranslationDataSource
import ru.s4nchez.translator.data.translator.datasource.NetworkTranslationDataSource
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.repository.TranslationRepository
import ru.s4nchez.translator.data.translator.repository.TranslationRepositoryImpl
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractorImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
class TranslatorModule {

    companion object {
        private const val NETWORK_DATA_SOURCE = "NETWORK_DATA_SOURCE"
        private const val MEMORY_DATA_SOURCE = "MEMORY_DATA_SOURCE"
    }

    @Provides
    @Singleton
    @Named(NETWORK_DATA_SOURCE)
    fun provideNetworkTranslationDataSource(retrofit: Retrofit): TranslationDataSource {
        return NetworkTranslationDataSource(retrofit)
    }

    @Provides
    @Singleton
    @Named(MEMORY_DATA_SOURCE)
    fun provideMemoryTranslationDataSource(): TranslationDataSource {
        return MemoryTranslationDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslationRepository(
            @Named(NETWORK_DATA_SOURCE) networkTranslationDataSource: TranslationDataSource,
            @Named(MEMORY_DATA_SOURCE) memoryTranslationDataSource: TranslationDataSource
    ): TranslationRepository {

        return TranslationRepositoryImpl(networkTranslationDataSource, memoryTranslationDataSource)
    }

    @Provides
    @Singleton
    fun provideTranslatorInteractor(translationRepository: TranslationRepository): TranslatorInteractor {
        return TranslatorInteractorImpl(translationRepository)
    }
}