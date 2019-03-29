package ru.s4nchez.translator.domain.translator

import io.reactivex.Single
import ru.s4nchez.translator.data.translator.repository.TranslationRepository

class TranslatorInteractorImpl(
        private val translationRepository: TranslationRepository
) : TranslatorInteractor {

    override fun translate(str: String): Single<List<String>> {
        return translationRepository.translate(str)
    }
}