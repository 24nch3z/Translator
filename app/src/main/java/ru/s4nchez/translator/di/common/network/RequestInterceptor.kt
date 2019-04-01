package ru.s4nchez.translator.di.common.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.s4nchez.translator.BuildConfig

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", BuildConfig.ApiKey)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}