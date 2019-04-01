package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.datasource.TranslationDataSource
import ru.s4nchez.translator.data.translator.model.Languages

class TranslationRepositoryImpl(
        private val networkDataSource: TranslationDataSource,
        private val memoryDataSource: TranslationDataSource
) : TranslationRepository {

    override fun translate(str: String, from: String, to: String): Single<List<String>> {
        return networkDataSource.translate(str, from, to)
    }

    override fun getLanguages(): Single<Languages> {
        val fromNetworkWithSave = networkDataSource.getLanguages()
                .flatMapCompletable { memoryDataSource.putLanguages(it) }
                .andThen(memoryDataSource.getLanguages())

        return memoryDataSource.getLanguages()
                .onErrorResumeNext(fromNetworkWithSave)
    }
}