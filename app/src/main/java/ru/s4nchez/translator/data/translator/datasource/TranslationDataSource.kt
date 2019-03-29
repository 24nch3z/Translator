package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

interface TranslationDataSource {
    fun translate(str: String): Single<List<String>>
    fun getLanguages(uiLang: String): Single<Languages>
}