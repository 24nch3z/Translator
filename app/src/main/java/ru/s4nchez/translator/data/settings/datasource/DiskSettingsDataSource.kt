package ru.s4nchez.translator.data.settings.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.s4nchez.sharedprefhelper.SharedPrefHelper

class DiskSettingsDataSource(
        private val sharedPrefHelper: SharedPrefHelper
) : SettingsDataSource {

    companion object {
        private const val TRANSLATION_FROM = "TRANSLATION_FROM"
        private const val TRANSLATION_TO = "TRANSLATION_TO"
    }

    override fun setTranslationFrom(from: String): Completable {
        return Completable.fromAction { sharedPrefHelper.save(TRANSLATION_FROM, from) }
    }

    override fun getTranslationFrom(defaultFrom: String): Single<String> {
        return Single.fromCallable { sharedPrefHelper.getString(TRANSLATION_FROM, defaultFrom) }
    }

    override fun setTranslationTo(to: String): Completable {
        return Completable.fromAction { sharedPrefHelper.save(TRANSLATION_TO, to) }
    }

    override fun getTranslationTo(defaultTo: String): Single<String> {
        return Single.fromCallable { sharedPrefHelper.getString(TRANSLATION_TO, defaultTo) }
    }

    override fun setStringForTranslation(text: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun getStringForTranslation(): Single<String> {
        throw UnsupportedOperationException()
    }
}