package com.margarin.commonpregnancy.data

import com.margarin.commonpregnancy.R

fun Int.getChildImageResId(): Int {
    val imageList = listOf(
        R.drawable.week_1_3,
        R.drawable.week_1_3,
        R.drawable.week_1_3,
        R.drawable.week_4,
        R.drawable.week_5,
        R.drawable.week_6,
        R.drawable.week_7,
        R.drawable.week_8,
        R.drawable.week_9,
        R.drawable.week_10,
        R.drawable.week_11,
        R.drawable.week_12,
        R.drawable.week_13,
        R.drawable.week_14,
        R.drawable.week_15,
        R.drawable.week_16,
        R.drawable.week_17,
        R.drawable.week_18,
        R.drawable.week_19,
        R.drawable.week_20,
        R.drawable.week_21,
        R.drawable.week_22,
        R.drawable.week_23,
        R.drawable.week_24,
        R.drawable.week_25,
        R.drawable.week_26,
        R.drawable.week_27,
        R.drawable.week_28,
        R.drawable.week_29,
        R.drawable.week_30,
        R.drawable.week_31,
        R.drawable.week_32,
        R.drawable.week_33,
        R.drawable.week_34,
        R.drawable.week_35,
        R.drawable.week_36,
        R.drawable.week_37,
        R.drawable.week_38,
        R.drawable.week_39,
        R.drawable.week_40
    )
    return imageList[this]
}

fun Int.getMotherImageResId(): Int {
    return when (this) {
        in 0..3 -> R.drawable.mother_1
        in 4..7 -> R.drawable.mother_2
        in 8..12 -> R.drawable.mother_3
        in 13..16 -> R.drawable.mother_4
        in 17..20 -> R.drawable.mother_5
        in 21..25 -> R.drawable.mother_6
        in 26..29 -> R.drawable.mother_7
        in 30..34 -> R.drawable.mother_8
        in 35..39 -> R.drawable.mother_9
        else -> R.drawable.mother_1
    }
}