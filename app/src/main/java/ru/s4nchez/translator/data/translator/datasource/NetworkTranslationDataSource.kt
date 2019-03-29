package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Single
import retrofit2.Retrofit
import ru.s4nchez.translator.data.common.BaseNetworkDataSource
import ru.s4nchez.translator.data.translator.ApiInterface

class NetworkTranslationDataSource(retrofit: Retrofit) : BaseNetworkDataSource(), TranslationDataSource {

    private val api: ApiInterface = retrofit.create(ApiInterface::class.java)

    override fun translate(str: String): Single<List<String>> {
        return makeRequest(api.translate(str, "ru-en"))
            .map { it.text }
    }
}