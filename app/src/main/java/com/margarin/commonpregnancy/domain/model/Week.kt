package com.margarin.commonpregnancy.domain.model

import android.os.Parcelable
import com.margarin.commonpregnancy.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Week (
    val weight: String = "",
    val length: String = "",
    val motherDetails: String = "",
    val childDetails: String = "",
    val childImageResId: Int = R.drawable.week_1_3
   // val advice: String,
): Parcelable
