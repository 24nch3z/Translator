package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.s4nchez.translator.data.translator.model.Languages

class MemoryTranslationDataSource : TranslationDataSource {

    var languages: Languages? = null

    override fun translate(str: String, from: String, to: String): Single<List<String>> {
        throw UnsupportedOperationException()
    }

    override fun getLanguages(uiLang: String): Single<Languages> {
        return Single.create {
            if (languages == null) {
                it.onError(NullPointerException())
            } else {
                it.onSuccess(languages!!)
            }
        }
    }

    override fun putLanguages(languages: Languages): Completable {
        return Completable.fromAction { this.languages = languages }
    }
}