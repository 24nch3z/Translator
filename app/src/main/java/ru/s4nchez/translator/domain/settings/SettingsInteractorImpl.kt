package ru.s4nchez.translator.domain.settings

import io.reactivex.Completable
import io.reactivex.Single
import ru.s4nchez.translator.data.settings.repository.SettingsRepository

class SettingsInteractorImpl(
        private val settingsRepository: SettingsRepository
) : SettingsInteractor {

    override fun setTranslationFrom(from: String): Completable {
        return settingsRepository.setTranslationFrom(from)
    }

    override fun getTranslationFrom(): Single<String> {
        return settingsRepository.getTranslationFrom()
    }

    override fun setTranslationTo(to: String): Completable {
        return settingsRepository.setTranslationTo(to)
    }

    override fun getTranslationTo(): Single<String> {
        return settingsRepository.getTranslationTo()
    }

    override fun setStringForTranslation(text: String): Completable {
        return settingsRepository.setStringForTranslation(text)
    }

    override fun getStringForTranslation(): Single<String> {
        return settingsRepository.getStringForTranslation()
    }
}