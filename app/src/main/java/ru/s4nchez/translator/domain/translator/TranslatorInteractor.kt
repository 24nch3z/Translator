package ru.s4nchez.translator.domain.translator

import io.reactivex.Single

interface TranslatorInteractor {
    fun translate(str: String): Single<List<String>>
}