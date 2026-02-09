package com.rktuhin.htmlview_demo

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import androidx.core.text.HtmlCompat

object TextUtil {

    fun displayRichText(textView: TextView, htmlString: String) {
        // 1. Convert tags like <strong> and <p> first
        val spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val spannable = SpannableStringBuilder(spanned)

        // 2. Extract the number from "font-size: 12px" using Regex
        val pattern = Regex("font-size:\\s*(\\d+)px")
        val match = pattern.find(htmlString)

        // If we find a number (like 12), we apply it to the whole text
        val fontSize = match?.groupValues?.get(1)?.toInt() ?: 14 // Default to 14 if not found

        spannable.setSpan(
            AbsoluteSizeSpan(fontSize, true), // 'true' means SP (scalable pixels)
            0,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 3. Set the final result to your view
        textView.text = spannable
    }

}