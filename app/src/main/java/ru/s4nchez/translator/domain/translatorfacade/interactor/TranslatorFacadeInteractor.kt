package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Single
import ru.s4nchez.translator.domain.translatorfacade.model.Language

interface TranslatorFacadeInteractor {
    fun getFromLanguages(): Single<List<Language>>
    fun getToLanguages(): Single<List<Language>>
    fun translate(str: String): Single<List<String>>
    fun setFromLanguage(language: Language): Single<List<String>>
    fun setToLanguage(language: Language): Single<List<String>>
    fun initLanguages(): Single<List<String>>
}