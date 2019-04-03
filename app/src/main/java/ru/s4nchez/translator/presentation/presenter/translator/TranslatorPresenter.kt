package ru.s4nchez.translator.presentation.presenter.translator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.s4nchez.logger.Logger
import ru.s4nchez.mvp.BasePresenter
import ru.s4nchez.translator.domain.translatorfacade.interactor.TranslatorFacadeInteractor
import ru.s4nchez.translator.domain.translatorfacade.model.Language
import ru.s4nchez.translator.presentation.view.translator.TranslatorView

class TranslatorPresenter(
        private val translatorFacadeInteractor: TranslatorFacadeInteractor
) : BasePresenter<TranslatorView>() {

    fun initLanguages() {
        view?.hideUi()
        view?.showProgress()

        val d = translatorFacadeInteractor.initLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showLanguages(it)
                    view?.showUi()
                    view?.hideProgress()
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun translate(str: String) {
        val d = translatorFacadeInteractor.translate(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showTranslate(it[0])
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun getFromLanguages() {
        view?.disableLanguagesButtons()

        val d = translatorFacadeInteractor.getFromLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.openChooseFromLanguageDialog(it as ArrayList<Language>)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun getToLanguages() {
        view?.disableLanguagesButtons()

        val d = translatorFacadeInteractor.getToLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.openChooseToLanguageDialog(it as ArrayList<Language>)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun setFromLanguage(language: Language) {
        val d = translatorFacadeInteractor.setFromLanguage(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showLanguages(it)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun setToLanguage(language: Language) {
        val d = translatorFacadeInteractor.setToLanguage(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showLanguages(it)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun swapLanguages() {
        val d = translatorFacadeInteractor.swapLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showLanguages(it)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }
}