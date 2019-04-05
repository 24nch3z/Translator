package ru.s4nchez.translator.presentation.view.translator

import ru.s4nchez.translator.domain.translatorfacade.model.Language

interface TranslatorView {
    fun openChooseFromLanguageDialog(languages: ArrayList<Language>)
    fun openChooseToLanguageDialog(languages: ArrayList<Language>)
    fun showLanguages(languages: List<String>)
    fun showTranslate(translate: String)
    fun showProgress()
    fun hideProgress()
    fun showUi()
    fun hideUi()
    fun disableLanguagesButtons()
    fun enableLanguagesButtons()
    fun handleError(error: Throwable)
}