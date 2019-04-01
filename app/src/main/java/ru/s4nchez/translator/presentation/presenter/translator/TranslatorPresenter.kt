package ru.s4nchez.translator.presentation.presenter.translator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.s4nchez.mvp.BasePresenter
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.presentation.view.translator.TranslatorView

class TranslatorPresenter(
        private val translatorInteractor: TranslatorInteractor
) : BasePresenter<TranslatorView>() {

    fun getLanguages() {
        val d = translatorInteractor.getLanguages("ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val t = it
                }, {})
        disposable.add(d)
    }

    fun translate(str: String) {
        val d = translatorInteractor.translate(str, "ru", "en")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showTranslate(it[0])
                }, {})
        disposable.add(d)
    }
}