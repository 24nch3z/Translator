package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.model.Languages

class TranslationRepositoryImpl(
        private val dataSource: TranslationDataSource
) : TranslationRepository {

    override fun translate(str: String): Single<List<String>> {
        return dataSource.translate(str)
    }

    override fun getLanguages(uiLang: String): Single<Languages> {
        return dataSource.getLanguages(uiLang)
    }
}