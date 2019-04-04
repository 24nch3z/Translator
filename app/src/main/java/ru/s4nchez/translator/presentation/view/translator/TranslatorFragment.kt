package ru.s4nchez.translator.presentation.view.translator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.screen_translator.*
import ru.s4nchez.translator.App
import ru.s4nchez.translator.R
import ru.s4nchez.translator.domain.translatorfacade.model.Language
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter
import ru.s4nchez.translator.presentation.view.common.BaseFragment
import ru.s4nchez.translator.utils.onTextChanged
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslatorFragment : BaseFragment(), TranslatorView {

    override val layout = R.layout.screen_translator

    private val TAG_LANG_FROM = 1
    private val TAG_LANG_TO = 2

    private val REQUEST_CODE_DIALOG = 1

    @Inject
    lateinit var presenter: TranslatorPresenter

    private lateinit var translateSubject: PublishSubject<String>
    private lateinit var translateDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).componentManager.buildTranslatorComponent().inject(this)
        translateSubject = PublishSubject.create()
        presenter.bindView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translateDisposable = translateSubject.debounce(1, TimeUnit.SECONDS)
                .subscribe { presenter.translate(it) }

        input_view.onTextChanged { translateSubject.onNext(it) }
        lang_from_button.setOnClickListener { presenter.getFromLanguages() }
        lang_to_button.setOnClickListener { presenter.getToLanguages() }
        swap_button.setOnClickListener { presenter.swapLanguages() }

        presenter.initLanguages()
    }

    override fun onDestroy() {
        super.onDestroy()
        translateDisposable.dispose()
        (activity?.application as App).componentManager.destroyTranslatorComponent()
        presenter.unbindView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_DIALOG -> {
                    val position = data?.getIntExtra(ListDialogFragment.RESULT_POSITION, TAG_LANG_FROM)
                    val selectedLanguage = data?.getParcelableExtra<Language>(ListDialogFragment.RESULT_SELECTED)!!
                    when (position) {
                        TAG_LANG_FROM -> presenter.setFromLanguage(selectedLanguage)
                        TAG_LANG_TO -> presenter.setToLanguage(selectedLanguage)
                    }
                }
            }
        }

        if (requestCode == REQUEST_CODE_DIALOG) {
            enableLanguagesButtons()
        }
    }

    override fun openChooseFromLanguageDialog(languages: ArrayList<Language>) {
        openChooseLangDialog(languages, TAG_LANG_FROM)
    }

    override fun openChooseToLanguageDialog(languages: ArrayList<Language>) {
        openChooseLangDialog(languages, TAG_LANG_TO)
    }

    private fun openChooseLangDialog(langs: ArrayList<Language>, position: Int) {
        val dialog = ListDialogFragment.newInstance(langs, position)
        dialog.setTargetFragment(this, REQUEST_CODE_DIALOG)
        dialog.show(fragmentManager!!, null)
    }

    override fun showTranslate(translate: String) {
        translate_view.text = translate
        translation_view.visibility = if (translate.trim().isEmpty()) View.GONE else View.VISIBLE
    }

    override fun showLanguages(languages: List<String>) {
        lang_from_button.text = languages[0]
        lang_to_button.text = languages[1]
    }

    override fun showProgress() {
        progress_view.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_view.visibility = View.INVISIBLE
    }

    override fun showUi() {
        ui_container.visibility = View.VISIBLE
    }

    override fun hideUi() {
        ui_container.visibility = View.INVISIBLE
    }

    override fun disableLanguagesButtons() {
        lang_from_button.isClickable = false
        swap_button.isClickable = false
        lang_to_button.isClickable = false
    }

    override fun enableLanguagesButtons() {
        lang_from_button.isClickable = true
        swap_button.isClickable = true
        lang_to_button.isClickable = true
    }
}