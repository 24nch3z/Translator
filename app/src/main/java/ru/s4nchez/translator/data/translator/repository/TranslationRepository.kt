package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single

interface TranslationRepository {
    fun translate(str: String): Single<List<String>>
}