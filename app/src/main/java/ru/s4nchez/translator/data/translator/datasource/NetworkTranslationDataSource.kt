package ru.s4nchez.translator.data.translator.datasource

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import ru.s4nchez.translator.data.common.BaseNetworkDataSource
import ru.s4nchez.translator.data.translator.ApiInterface
import ru.s4nchez.translator.data.translator.model.Languages

class NetworkTranslationDataSource(retrofit: Retrofit) : BaseNetworkDataSource(), TranslationDataSource {

    private val api: ApiInterface = retrofit.create(ApiInterface::class.java)

    override fun translate(str: String, from: String, to: String): Single<List<String>> {
        if (str.trim().isEmpty()) {
            return Single.just(arrayListOf(""))
        }
        return makeRequest(api.translate(str, "$from-$to")).map { it.text }
    }

    override fun getLanguages(): Single<Languages> {
        return makeRequest(api.getLanguages("ru"))
                .map { languages ->
                    val languagesInDirs = HashSet<String>()

                    languages.dirs.forEach {
                        val parts = it.split("-")
                        languagesInDirs.add(parts[0])
                        languagesInDirs.add(parts[1])
                    }

                    val iterator = languages.langs.keys.iterator()
                    while (iterator.hasNext()) {
                        val next = iterator.next()
                        if (!languagesInDirs.contains(next)) {
                            iterator.remove()
                        }
                    }

                    languages
                }
    }

    override fun putLanguages(languages: Languages): Completable {
        throw UnsupportedOperationException()
    }
}