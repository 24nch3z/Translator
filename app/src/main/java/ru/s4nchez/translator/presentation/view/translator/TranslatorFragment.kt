package ru.s4nchez.translator.presentation.view.translator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.screen_translator.*
import ru.s4nchez.translator.App
import ru.s4nchez.translator.R
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter
import ru.s4nchez.translator.presentation.view.common.BaseFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslatorFragment : BaseFragment(), TranslatorView {

    override val layout = R.layout.screen_translator

    @Inject
    lateinit var presenter: TranslatorPresenter

    private lateinit var observer: PublishSubject<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).componentManager.buildTranslatorComponent().inject(this)
        observer = PublishSubject.create()
        presenter.bindView(this)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer.debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { presenter.translate(it) }

        input_view.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                observer.onNext(s.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).componentManager.destroyTranslatorComponent()
        presenter.unbindView()
    }

    override fun showTranslate(translate: String) {
        translate_view.text = translate
    }
}