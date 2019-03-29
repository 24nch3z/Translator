package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

interface TranslationRepository {
    fun translate(str: String): Single<List<String>>
    fun getLanguages(uiLang: String): Single<Languages>
}