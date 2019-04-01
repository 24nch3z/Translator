package ru.s4nchez.translator.data.settings.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.s4nchez.translator.data.settings.datasource.SettingsDataSource

class SettingsRepositoryImpl(
        private val diskSettingsDataSource: SettingsDataSource
) : SettingsRepository {

    override fun setTranslationFrom(from: String): Completable {
        return diskSettingsDataSource.setTranslationFrom(from)
    }

    override fun getTranslationFrom(): Single<String> {
        return diskSettingsDataSource.getTranslationFrom("ru")
    }

    override fun setTranslationTo(to: String): Completable {
        return diskSettingsDataSource.setTranslationTo(to)
    }

    override fun getTranslationTo(): Single<String> {
        return diskSettingsDataSource.getTranslationTo("en")
    }
}