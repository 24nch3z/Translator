package ru.s4nchez.translator.data.translator

import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import ru.s4nchez.translator.data.translator.model.Translation

const val BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/"

interface ApiInterface {

    @POST("translate")
    @Headers("Content-Type: application/json; charset=utf-8")
    fun translate(
            @Query("key") key: String,
            @Query("text") text: String,
            @Query("lang") lang: String
    ): Call<Translation>
}