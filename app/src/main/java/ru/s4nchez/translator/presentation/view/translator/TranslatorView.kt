package ru.s4nchez.translator.presentation.view.translator

import ru.s4nchez.translator.domain.translatorfacade.model.Language

interface TranslatorView {
    fun showTranslate(translate: String)
    fun showProgress()
    fun hideProgress()
    fun showUi()
    fun hideUi()
    fun openDialog(langs: ArrayList<Language>)
}