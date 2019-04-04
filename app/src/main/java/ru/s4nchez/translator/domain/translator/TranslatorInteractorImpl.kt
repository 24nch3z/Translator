package ru.s4nchez.translator.domain.translator

import io.reactivex.Observable
import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages
import ru.s4nchez.translator.data.translator.repository.TranslationRepository

class TranslatorInteractorImpl(
        private val translationRepository: TranslationRepository
) : TranslatorInteractor {

    override fun translate(str: String, fromLanguage: String, toLanguage: String): Single<String> {
        return translationRepository.translate(str, fromLanguage, toLanguage)
                .map { it[0] }
    }

    override fun getLanguages(): Single<Languages> {
        return translationRepository.getLanguages()
    }

    override fun getAvailableLanguagesForTranslation(fromLanguage: String): Single<List<String>> {
        return translationRepository.getLanguages()
                .map(Languages::dirs)
                .flatMapObservable { Observable.fromIterable(it) }
                .filter { it.split("-")[0].equals(fromLanguage) }
                .map { it.split("-")[1] }
                .toList()
    }

    override fun getLanguageLabelByCode(languageCode: String): Single<String> {
        return translationRepository.getLanguages()
                .map(Languages::langs)
                .flatMap { Single.fromCallable { it[languageCode] } }
    }

    override fun canMakeTranslate(fromLanguage: String, toLanguage: String): Single<Boolean> {
        return translationRepository.getLanguages()
                .map(Languages::dirs)
                .flatMapObservable { Observable.fromIterable(it) }
                .any { it == "$fromLanguage-$toLanguage" }
    }
}