package ru.s4nchez.translator.presentation.presenter.translator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.s4nchez.logger.Logger
import ru.s4nchez.mvp.BasePresenter
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translatorfacade.interactor.TranslatorFacadeInteractor
import ru.s4nchez.translator.domain.translatorfacade.model.Language
import ru.s4nchez.translator.presentation.view.translator.TranslatorView

class TranslatorPresenter(
        private val translatorInteractor: TranslatorInteractor,
        private val translatorFacadeInteractor: TranslatorFacadeInteractor
) : BasePresenter<TranslatorView>() {

    fun getLanguages() {
        view?.hideUi()
        view?.showProgress()

        val d = translatorInteractor.getLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showUi()
                    view?.hideProgress()
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun translate(str: String) {
        val d = translatorInteractor.translate(str, "ru", "en")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showTranslate(it[0])
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }

    fun getFromLanguages() {
        val d = translatorFacadeInteractor.getFromLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.openDialog(it as ArrayList<Language>)
                }, { Logger.d(it); throw it })
        disposable.add(d)
    }
}