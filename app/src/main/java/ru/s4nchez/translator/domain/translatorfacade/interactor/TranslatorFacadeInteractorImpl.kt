package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.s4nchez.translator.data.translator.model.Languages
import ru.s4nchez.translator.domain.settings.SettingsInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translatorfacade.model.Language

class TranslatorFacadeInteractorImpl(
        private val translatorInteractor: TranslatorInteractor,
        private val settingsInteractor: SettingsInteractor
) : TranslatorFacadeInteractor {

    override fun getFromLanguages(): Single<List<Language>> {
        return translatorInteractor.getLanguages()
                .map(Languages::langs)
                .map { langs -> langs.map { lang -> Language(lang.key, lang.value) } }
    }

    /*
        "dirs": [
            "ru-en",
            "ru-pl",
            "ru-hu",
            ...
        ],
     */
    // TODO: Пока что метод возвращает все языки, потом нужно будет фильтровать
    override fun getToLanguages(): Single<List<Language>> {
        return translatorInteractor.getLanguages()
                .map(Languages::langs)
                .map { langs -> langs.map { lang -> Language(lang.key, lang.value) } }
    }

    override fun translate(str: String): Single<List<String>> {
        return Single.zip(settingsInteractor.getTranslationFrom(), settingsInteractor.getTranslationTo(),
                BiFunction<String, String, Array<String?>> { t1, t2 -> arrayOf(t1, t2) })
                .flatMap { translatorInteractor.translate(str, it[0]!!, it[1]!!) }
    }
}