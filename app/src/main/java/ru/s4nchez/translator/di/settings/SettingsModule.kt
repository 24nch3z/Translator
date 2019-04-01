package ru.s4nchez.translator.di.settings

import dagger.Module
import dagger.Provides
import ru.s4nchez.sharedprefhelper.SharedPrefHelper
import ru.s4nchez.translator.data.settings.datasource.DiskSettingsDataSource
import ru.s4nchez.translator.data.settings.datasource.SettingsDataSource
import ru.s4nchez.translator.data.settings.repository.SettingsRepository
import ru.s4nchez.translator.data.settings.repository.SettingsRepositoryImpl
import ru.s4nchez.translator.domain.settings.SettingsInteractor
import ru.s4nchez.translator.domain.settings.SettingsInteractorImpl
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDataSource(sharedPrefHelper: SharedPrefHelper): SettingsDataSource {
        return DiskSettingsDataSource(sharedPrefHelper)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(diskSettingsDataSource: SettingsDataSource): SettingsRepository {
        return SettingsRepositoryImpl(diskSettingsDataSource)
    }

    @Provides
    @Singleton
    fun provideSettingsInteractor(settingsRepository: SettingsRepository): SettingsInteractor {
        return SettingsInteractorImpl(settingsRepository)
    }
}