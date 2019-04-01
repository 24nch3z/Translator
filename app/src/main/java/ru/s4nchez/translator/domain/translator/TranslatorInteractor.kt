package ru.s4nchez.translator.domain.translator

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

interface TranslatorInteractor {
    fun translate(str: String, from: String, to: String): Single<List<String>>
    fun getLanguages(): Single<Languages>
}