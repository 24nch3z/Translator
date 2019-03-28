package ru.s4nchez.mvp

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> {

    protected var view: T? = null
    protected var disposable = CompositeDisposable()

    open fun bindView(view: T) {
        if (view == null) {
            throw IllegalArgumentException("view не может быть null")
        }
        this.view = view
    }

    open fun unbindView() {
        view = null
        disposable.clear()
    }
}