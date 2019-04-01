package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.model.Languages

class TranslationRepositoryImpl(
    private val networkDataSource: TranslationDataSource,
    private val memoryDataSource: TranslationDataSource
) : TranslationRepository {

    override fun translate(str: String): Single<List<String>> {
        return networkDataSource.translate(str)
    }

    override fun getLanguages(uiLang: String): Single<Languages> {
        val fromNetworkWithSave = networkDataSource.getLanguages(uiLang)
            .flatMapCompletable { memoryDataSource.putLanguages(it) }
            .andThen(memoryDataSource.getLanguages(uiLang))

        return memoryDataSource.getLanguages(uiLang)
            .onErrorResumeNext(fromNetworkWithSave)
    }
}