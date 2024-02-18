package com.margarin.commonpregnancy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Week (
    val id: Int,
    val text: String
): Parcelable
