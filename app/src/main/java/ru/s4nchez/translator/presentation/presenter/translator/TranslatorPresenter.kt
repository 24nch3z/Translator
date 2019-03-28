package ru.s4nchez.translator.presentation.presenter.translator

import io.reactivex.Single
import ru.s4nchez.mvp.BasePresenter
import ru.s4nchez.translator.presentation.view.translator.TranslatorView

class TranslatorPresenter : BasePresenter<TranslatorView>() {

    fun translate(str: String) {
        val d = Single.just(str)
                .map { it.reversed() }
                .subscribe({
                    view?.showTranslate(it)
                }, {})
        disposable.add(d)
    }
}