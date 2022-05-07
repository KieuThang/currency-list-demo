package com.hometest.currencylistdemo.common

import android.view.MotionEvent
import android.view.View

class ViewPressEffectHelper {

    companion object {
        fun attach(view: View) {
            view.setOnTouchListener(ASetOnTouchListener(view))
        }
    }

    private class ASetOnTouchListener(v: View) : View.OnTouchListener {
//        val ZERO_ALPHA = 1.0f
        val HALF_ALPHA = 0.6f
        val FIXED_DURATION = 100
        var alphaOrginally = 1.0f

        init {
            alphaOrginally = v.alpha

        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate().setDuration(FIXED_DURATION.toLong()).alpha(HALF_ALPHA)

                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate().setDuration(100).alpha(alphaOrginally)
                }
            }
            return false
        }

    }
}

