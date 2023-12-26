package com.raveendra.finalproject_binar.utils

import android.graphics.Typeface
import android.view.Gravity
import es.dmoral.toasty.Toasty

/**
 *hrahm,19/12/2023, 20:36
 **/
object ToastyUtil {
    fun configureToasty() {
        Toasty.Config.getInstance()
            .tintIcon(true)
            .setToastTypeface(Typeface.DEFAULT)
            .setTextSize(16)
            .allowQueue(true)
            .setGravity(
                Gravity.CENTER or Gravity.BOTTOM,
                0,
                0
            )
            .supportDarkTheme(true)
            .setRTL(true)
            .apply()
    }
}
