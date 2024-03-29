package com.margarin.commonpregnancy.model

import android.os.Parcelable
import com.margarin.domain.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Week (
    val id: Int = 0,
    val weight: String = "",
    val length: String = "",
    val motherDetails: String = "",
    val childDetails: String = "",
    val adviceDetails: String = "",
    val childImageResId: Int = R.drawable.week_1_3,
    val motherImageResId: Int = R.drawable.mother_1,
    val color: Long = 0
): Parcelable
