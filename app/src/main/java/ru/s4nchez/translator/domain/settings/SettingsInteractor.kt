package ru.s4nchez.translator.domain.settings

import io.reactivex.Completable
import io.reactivex.Single

interface SettingsInteractor {
    fun setTranslationFrom(from: String): Completable
    fun getTranslationFrom(): Single<String>
    fun setTranslationTo(to: String): Completable
    fun getTranslationTo(): Single<String>
}