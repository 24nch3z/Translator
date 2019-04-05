package ru.s4nchez.translator.di.settings

import dagger.Module
import dagger.Provides
import ru.s4nchez.sharedprefhelper.SharedPrefHelper
import ru.s4nchez.translator.data.settings.datasource.DiskSettingsDataSource
import ru.s4nchez.translator.data.settings.datasource.MemorySettingsDataSource
import ru.s4nchez.translator.data.settings.datasource.SettingsDataSource
import ru.s4nchez.translator.data.settings.repository.SettingsRepository
import ru.s4nchez.translator.data.settings.repository.SettingsRepositoryImpl
import ru.s4nchez.translator.domain.settings.SettingsInteractor
import ru.s4nchez.translator.domain.settings.SettingsInteractorImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
class SettingsModule {

    companion object {
        private const val DISK_DATA_SOURCE = "DISK_DATA_SOURCE"
        private const val MEMORY_DATA_SOURCE = "MEMORY_DATA_SOURCE"
    }

    @Provides
    @Singleton
    @Named(DISK_DATA_SOURCE)
    fun provideDiskSettingsDataSource(sharedPrefHelper: SharedPrefHelper): SettingsDataSource {
        return DiskSettingsDataSource(sharedPrefHelper)
    }

    @Provides
    @Singleton
    @Named(MEMORY_DATA_SOURCE)
    fun provideMemorySettingsDataSource(): SettingsDataSource {
        return MemorySettingsDataSource()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
            @Named(DISK_DATA_SOURCE) diskSettingsDataSource: SettingsDataSource,
            @Named(MEMORY_DATA_SOURCE) memorySettingsDataSource: SettingsDataSource
    ): SettingsRepository {
        return SettingsRepositoryImpl(diskSettingsDataSource, memorySettingsDataSource)
    }

    @Provides
    @Singleton
    fun provideSettingsInteractor(settingsRepository: SettingsRepository): SettingsInteractor {
        return SettingsInteractorImpl(settingsRepository)
    }
}