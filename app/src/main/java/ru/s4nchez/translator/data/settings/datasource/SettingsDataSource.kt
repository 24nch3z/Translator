package ru.s4nchez.translator.data.settings.datasource

import io.reactivex.Completable
import io.reactivex.Single

interface SettingsDataSource {
    fun setTranslationFrom(from: String): Completable
    fun getTranslationFrom(defaultFrom: String): Single<String>
    fun setTranslationTo(to: String): Completable
    fun getTranslationTo(defaultTo: String): Single<String>
    fun setStringForTranslation(text: String): Completable
    fun getStringForTranslation(): Single<String>
}