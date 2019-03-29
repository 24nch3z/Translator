package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource

class TranslationRepositoryImpl(
        private val dataSource: TranslationDataSource
) : TranslationRepository {

    override fun translate(str: String): Single<List<String>> {
        if (str.trim().isEmpty()) {
            return Single.just(arrayListOf(""))
        }
        return dataSource.translate(str)
    }
}