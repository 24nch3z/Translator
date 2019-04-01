package ru.s4nchez.translator.data.translator.repository

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

interface TranslationRepository {
    fun translate(str: String, from: String, to: String): Single<List<String>>
    fun getLanguages(): Single<Languages>
}