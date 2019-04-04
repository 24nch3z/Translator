package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Observable
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
                .flatMapObservable { Observable.fromIterable(it) }
                .sorted { o1, o2 -> o1.label.compareTo(o2.label) }
                .toList()
    }

    /*
        "dirs": [
            "ru-en",
            "ru-pl",
            "ru-hu",
            ...
        ],

       "langs": {
            "ru": "русский",
            "en": "английский",
            "pl": "польский",
            ...
        }
     */
    override fun getToLanguages(): Single<List<Language>> {
        val getLanguages = translatorInteractor.getLanguages().map(Languages::langs)
        fun getAvailableLanguages(fromLanguage: String) = translatorInteractor
                .getAvailableLanguagesForTranslation(fromLanguage).map { HashSet(it) }

        return settingsInteractor.getTranslationFrom()
                .flatMap { fromLanguage ->
                    Single.zip(
                            getLanguages, getAvailableLanguages(fromLanguage),
                            BiFunction<HashMap<String, String>, HashSet<String>, List<Language>> { languages, availableLanguages ->
                                languages
                                        .filter { availableLanguages.contains(it.key) }
                                        .map { lang -> Language(lang.key, lang.value) }
                            }
                    )
                }
                .flatMapObservable { Observable.fromIterable(it) }
                .sorted { o1, o2 -> o1.label.compareTo(o2.label) }
                .toList()
    }

    // TODO: Рефакторинг
    override fun translate(str: String): Single<String> {
        return Single.zip(settingsInteractor.getTranslationFrom(), settingsInteractor.getTranslationTo(),
                BiFunction<String, String, Array<String?>> { t1, t2 -> arrayOf(t1, t2) })
                .flatMap { translatorInteractor.translate(str, it[0]!!, it[1]!!) }
    }

    // TODO: Рефакторинг
    override fun setFromLanguage(language: Language): Single<List<String>> {
        return settingsInteractor.setTranslationFrom(language.code)
                .andThen(translatorInteractor.getAvailableLanguagesForTranslation(language.code))
                .flatMap { availableLanguages ->
                    Single.zip(settingsInteractor.getTranslationFrom(), settingsInteractor.getTranslationTo(),
                            BiFunction<String, String, List<String>> { t1, t2 ->
                                listOf(t1, availableLanguages.find { it == t2 }
                                        ?: availableLanguages[0])
                            })
                }
                .flatMap { settingsInteractor.setTranslationTo(it[1]).toSingle { it } }
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMapSingle { translatorInteractor.getLanguageLabelByCode(it) }
                .toList()
    }

    override fun setToLanguage(language: Language): Single<List<String>> {
        return settingsInteractor.setTranslationTo(language.code)
                .toSingle {}
                .flatMapPublisher {
                    Single.concat(settingsInteractor.getTranslationFrom(),
                            settingsInteractor.getTranslationTo())
                }
                .flatMapSingle { translatorInteractor.getLanguageLabelByCode(it) }
                .toList()
    }

    override fun initLanguages(): Single<List<String>> {
        return translatorInteractor.getLanguages()
                .flatMapPublisher {
                    Single.concat(settingsInteractor.getTranslationFrom(),
                            settingsInteractor.getTranslationTo())
                }
                .flatMapSingle { translatorInteractor.getLanguageLabelByCode(it) }
                .toList()
    }

    // TODO: Рефакторинг
    override fun swapLanguages(): Single<List<String>> {
        return Single.concat(settingsInteractor.getTranslationFrom(), settingsInteractor.getTranslationTo())
                .toList()
                .flatMap {
                    settingsInteractor.setTranslationFrom(it[1])
                            .andThen(settingsInteractor.setTranslationTo(it[0]))
                            .toSingle { arrayListOf(it[1], it[0]) }
                }
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMapSingle { translatorInteractor.getLanguageLabelByCode(it) }
                .toList()
    }
}