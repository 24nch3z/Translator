package ru.s4nchez.translator.presentation.view.translator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.screen_translator.*
import ru.s4nchez.translator.App
import ru.s4nchez.translator.R
import ru.s4nchez.translator.domain.translatorfacade.model.Language
import ru.s4nchez.translator.presentation.presenter.translator.TranslatorPresenter
import ru.s4nchez.translator.presentation.view.common.BaseFragment
import ru.s4nchez.translator.presentation.view.common.NetworkStatusChangeListener
import ru.s4nchez.translator.utils.isInternetConnected
import ru.s4nchez.translator.utils.onTextChanged
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslatorFragment : BaseFragment(), TranslatorView, NetworkStatusChangeListener {

    override val layout = R.layout.screen_translator

    private val TAG_LANG_FROM = 1
    private val TAG_LANG_TO = 2

    private val REQUEST_CODE_DIALOG = 1

    @Inject
    lateinit var presenter: TranslatorPresenter

    private lateinit var translateSubject: BehaviorSubject<String>
    private lateinit var translateDisposable: Disposable

    private var repeatInitLanguagesRequestFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).componentManager.buildTranslatorComponent().inject(this)
        presenter.bindView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        translateSubject = BehaviorSubject.create()
        translateDisposable = translateSubject
                .debounce(1, TimeUnit.SECONDS)
                .switchMap { presenter.translate(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showTranslate(it) }, { handleError(it) })
        input_view.onTextChanged { text -> translateSubject.onNext(text) }

        lang_from_button.setOnClickListener { presenter.getFromLanguages() }
        lang_to_button.setOnClickListener { presenter.getToLanguages() }
        swap_button.setOnClickListener { presenter.swapLanguages() }

        presenter.initLanguages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
        translateDisposable.dispose()
        (activity?.application as App).componentManager.destroyTranslatorComponent()
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

        // Делаем доступными для нажатия кнопки переключения языков при отмене выбора языка в диалоге
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
        translation_view.visibility = if (translate.isBlank()) View.GONE else View.VISIBLE
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

    override fun setRepeatInitLanguagesRequestFlag() {
        repeatInitLanguagesRequestFlag = true
    }

    override fun handleError(error: Throwable) {
        when (error) {
            is UnknownHostException -> {
                if (!isInternetConnected(context!!)) {
                    showMessage(getString(R.string.request_error_by_network_lack))
                }
            }
            else -> showMessage(getString(R.string.common_error))
        }
    }

    override fun retranslate() {
        translateSubject.onNext(input_view.text.toString())
    }

    override fun networkStatusChange(isInternetConnected: Boolean) {
        if (isInternetConnected && repeatInitLanguagesRequestFlag) {
            repeatInitLanguagesRequestFlag = false
            presenter.initLanguages()
        }
    }
}