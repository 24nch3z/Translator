package ru.s4nchez.translator.data.settings.datasource

import io.reactivex.Completable
import io.reactivex.Single

class MemorySettingsDataSource : SettingsDataSource {

    private var stringForTranslation: String = ""

    override fun setTranslationFrom(from: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun getTranslationFrom(defaultFrom: String): Single<String> {
        throw UnsupportedOperationException()
    }

    override fun setTranslationTo(to: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun getTranslationTo(defaultTo: String): Single<String> {
        throw UnsupportedOperationException()
    }

    override fun setStringForTranslation(text: String): Completable {
        return Completable.fromAction { stringForTranslation = text }
    }

    override fun getStringForTranslation(): Single<String> {
        return Single.fromCallable { stringForTranslation }
    }
}