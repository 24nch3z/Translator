package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Single
import ru.s4nchez.translator.domain.translatorfacade.model.Language

interface TranslatorFacadeInteractor {
    fun getFromLanguages(): Single<List<Language>>
    fun translate(str: String): Single<List<String>>
}