package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Single
import ru.s4nchez.translator.domain.translatorfacade.model.Language

interface TranslatorFacadeInteractor {
    fun getFromLanguages(): Single<List<Language>>
    fun getToLanguages(): Single<List<Language>>
    fun translate(str: String): Single<String>
    fun setFromLanguage(language: Language): Single<List<String>>
    fun setToLanguage(language: Language): Single<List<String>>
    fun initLanguages(): Single<List<String>>
    fun swapLanguages(): Single<List<String>>
    fun translateSavedText(str: String): Single<String>
}