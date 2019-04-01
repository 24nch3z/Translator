package ru.s4nchez.translator.data.settings.repository

import io.reactivex.Completable
import io.reactivex.Single

interface SettingsRepository {
    fun setTranslationFrom(from: String): Completable
    fun getTranslationFrom(): Single<String>
    fun setTranslationTo(to: String): Completable
    fun getTranslationTo(): Single<String>
}