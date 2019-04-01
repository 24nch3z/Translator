package ru.s4nchez.translator.presentation.view.translator

interface TranslatorView {
    fun showTranslate(translate: String)
    fun showProgress()
    fun hideProgress()
    fun showUi()
    fun hideUi()
}