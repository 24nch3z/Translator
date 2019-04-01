package ru.s4nchez.translator.domain.translatorfacade.interactor

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages
import ru.s4nchez.translator.domain.settings.SettingsInteractor
import ru.s4nchez.translator.domain.translator.TranslatorInteractor
import ru.s4nchez.translator.domain.translatorfacade.model.Language

class TranslatorFacadeInteractorImpl(
        private val translatorInteractor: TranslatorInteractor,
        private val settingsInteractor: SettingsInteractor
) : TranslatorFacadeInteractor {

    override fun getFromLanguages(): Single<List<Language>> {
        return translatorInteractor.getLanguages()
                .map(Languages::langs)
                .map { langs -> langs.map { lang -> Language(lang.key, lang.value) } }
    }
}