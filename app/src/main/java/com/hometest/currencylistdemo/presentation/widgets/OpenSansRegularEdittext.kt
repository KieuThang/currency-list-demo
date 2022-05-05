package com.hometest.currencylistdemo.presentation.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

open class OpenSansRegularEdittext @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        setFont()
    }

    private fun setFont() {
        val font = Typeface.createFromAsset(context.assets, "font/opensans_regular.ttf")
        setTypeface(font, Typeface.NORMAL)
    }
}