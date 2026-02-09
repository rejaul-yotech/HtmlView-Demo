package com.rktuhin.htmlview_demo

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val htmlText =
            "<p style=\\\"font-size: 12px; margin-bottom: 0px\\\"><strong>Ingredients</strong>: <span style=\\\"background-color:rgb(255,255,255);color:rgb(0,0,0);\\\">All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.</span></p><p style=\\\"font-size: 10px; margin-bottom: 0px\\\"><strong>Awareness</strong>: Please check the <strong>weight </strong>before purchase <strong>Allergies</strong>:  Barley, Celery. <strong>May contain</strong>: Cheese, Crustaceans.</p><p style=\\\"font-size: 10px; margin-bottom: 0px\\\"><strong>Instructions</strong>: Please handle with <strong>safety</strong></p><p style=\\\"font-size: 10px; margin-bottom: 0px\\\"><strong>Temperature Note</strong>: Store above <strong>10°C</strong>, Once opened use within 3 days. Defrost below <strong>25°C</strong>, Once defrosted use within 100 mins. Cook between <strong>50°C - 50°C</strong>, Once cooked use within 1 mins.</p>"

        val tvIngredients = findViewById<TextView>(R.id.tvIngredients)

        displayRichText(tvIngredients, htmlText)

        tvIngredients.setOnClickListener {
            Toast.makeText(this@MainActivity, "Pew Pew!", Toast.LENGTH_SHORT).show()
        }
    }
}


fun setHtmlText(textView: TextView, htmlString: String) {
    // 1. Convert the HTML string into a Spanned object
    val spannedText = HtmlCompat.fromHtml(
        htmlString,
        HtmlCompat.FROM_HTML_MODE_LEGACY // Handles tags like <b>, <strong>, <span>, and <p>
    )

    // 2. Set the text to the TextView
    textView.text = spannedText
}

fun displayRichText(textView: TextView, htmlString: String) {
    // 1. Convert tags like <strong> and <p> first
    val spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val spannable = SpannableStringBuilder(spanned)

    // 2. Extract the number from "font-size: 10px" using Regex
    val pattern = Regex("font-size:\\s*(\\d+)px")
    val match = pattern.find(htmlString)

    // If we find a number (like 10), we apply it to the whole text
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
