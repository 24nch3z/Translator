package ru.s4nchez.translator.domain.translatorfacade.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language(
        val value: String,
        val label: String
) : Parcelable