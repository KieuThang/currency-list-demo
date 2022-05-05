package com.hometest.currencylistdemo.presentation.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

open class OpenSansBoldTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    init {
        setFont()
    }

    private fun setFont() {
        val font = Typeface.createFromAsset(context.assets, "font/opensans_bold.ttf")
        setTypeface(font, Typeface.NORMAL)
    }
}