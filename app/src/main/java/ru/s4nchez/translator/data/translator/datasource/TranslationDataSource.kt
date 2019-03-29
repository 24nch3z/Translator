package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Single

interface TranslationDataSource {
    fun translate(str: String): Single<List<String>>
}