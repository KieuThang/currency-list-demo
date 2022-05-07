package com.hometest.currencylistdemo.presentation.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

open class OpenSansRegularButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    init {
        setFont()
        setMinWidth()
    }

    private fun setFont() {
        val font = Typeface.createFromAsset(context.assets, "font/opensans_regular.ttf")
        setTypeface(font, Typeface.NORMAL)
    }

    private fun setMinWidth() {}
}