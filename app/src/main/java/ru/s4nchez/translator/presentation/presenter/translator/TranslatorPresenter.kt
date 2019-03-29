package ru.s4nchez.translator.presentation.presenter.translator

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.s4nchez.mvp.BasePresenter
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.presentation.view.translator.TranslatorView

class TranslatorPresenter(
        private val translatorInteractor: TranslatorInteractor
) : BasePresenter<TranslatorView>() {

    fun translate(str: String) {
//        val d = Single.just(str)
//                .map { it.reversed() }
//                .subscribe({
//                    view?.showTranslate(it)
//                }, {})

        val d = translatorInteractor.translate(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showTranslate(it[0])
                }, {})

        disposable.add(d)
    }
}