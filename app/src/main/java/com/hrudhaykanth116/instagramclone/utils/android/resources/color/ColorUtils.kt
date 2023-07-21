package com.hrudhaykanth116.instagramclone.utils.android.resources.color

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use

@ColorInt
fun Context.getThemedColor(
    @AttrRes attrRef: Int,
    defaultColor: Int = android.graphics.Color.TRANSPARENT
): Int {

    // val value = TypedValue()
    // theme.resolveAttribute(attrRef, value, true)
    // return value.data

    // Took from google: https://youtu.be/Owkf8DhAOSo?t=645
    return obtainStyledAttributes(
        intArrayOf(attrRef)
    ).use {
        it.getColor(0, defaultColor)
    }

    // return MaterialColors.getColor(
    //     this,
    //     attrRef,
    //     ContextCompat.getColor(this, defaultColor)
    // )
}