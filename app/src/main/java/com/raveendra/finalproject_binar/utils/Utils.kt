package com.raveendra.finalproject_binar.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale

fun Double.toIdrCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0
    val formatted = numberFormat.format(this)
    val subStr = formatted.substring(2, formatted.length)
    return "Rp " + subStr.replace(",", ".")
}
fun TextView.highLightWord(word: String, onClick: () -> Unit) {
    val ssBuilder = SpannableStringBuilder(this.text)
    val clickAbleSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = ds.linkColor
            ds.isUnderlineText = false
        }
    }
    ssBuilder.setSpan(
        clickAbleSpan,
        text.indexOf(word),
        text.indexOf(word) + word.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    text = ssBuilder
    movementMethod = LinkMovementMethod.getInstance()
}
