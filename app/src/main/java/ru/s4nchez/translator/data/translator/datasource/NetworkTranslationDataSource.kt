package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import ru.s4nchez.translator.data.common.BaseNetworkDataSource
import ru.s4nchez.translator.data.translator.ApiInterface
import ru.s4nchez.translator.data.translator.model.Languages
import java.lang.UnsupportedOperationException

class NetworkTranslationDataSource(retrofit: Retrofit) : BaseNetworkDataSource(), TranslationDataSource {

    private val api: ApiInterface = retrofit.create(ApiInterface::class.java)

    override fun translate(str: String): Single<List<String>> {
        if (str.trim().isEmpty()) {
            return Single.just(arrayListOf(""))
        }
        return makeRequest(api.translate(str, "ru-en")).map { it.text }
    }

    override fun getLanguages(uiLang: String): Single<Languages> {
        return makeRequest(api.getLanguages(uiLang))
    }

    override fun putLanguages(languages: Languages): Completable {
        throw UnsupportedOperationException()
    }
}