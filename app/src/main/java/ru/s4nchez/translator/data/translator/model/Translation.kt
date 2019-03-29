package ru.s4nchez.translator.data.translator.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Translation(

        @Expose
        @SerializedName("code")
        val code: Int,

        @Expose
        @SerializedName("lang")
        val lang: String,

        @Expose
        @SerializedName("text")
        val text: List<String>
)