package ru.s4nchez.translator.data.common

import io.reactivex.Single
import retrofit2.Call
import ru.s4nchez.logger.Logger
import java.io.IOException

abstract class BaseNetworkDataSource {

    fun <T> makeRequest(call: Call<T>): Single<T> {
        return Single.create { emitter ->
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { emitter.onSuccess(it) }
                } else {
                    Logger.d("Error with url: ", call.request().url())
                    Logger.d("Response code:  ", response.code())
                    Logger.d("Response error: ", response.errorBody()?.string())
                    emitter.onError(Throwable("Unknown error"))
                }
            } catch (e: IOException) {
                Logger.d("Error with url: ", call.request().url())
                if (!emitter.isDisposed)
                    emitter.onError(e)
            }
        }
    }
}