package ru.s4nchez.translator.domain.translator

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

interface TranslatorInteractor {

    fun translate(str: String, fromLanguage: String, toLanguage: String): Single<String>

    fun getLanguages(): Single<Languages>

    /**
     * Получить список доступных языков для перевода с текущего (параметр fromLanguage)
     */
    fun getAvailableLanguagesForTranslation(fromLanguage: String): Single<List<String>>

    /**
     * Получить лэйбл языка по его коду
     *
     * Все языки храняться в мапе, формат следующий: "ru": "русский"
     * "ru" - code, "русский" - label
     */
    fun getLanguageLabelByCode(languageCode: String): Single<String>

    /**
     * Показывает, доступен ли перевод с языка fromLanguage на язык toLanguage
     */
    fun canMakeTranslate(fromLanguage: String, toLanguage: String): Single<Boolean>
}