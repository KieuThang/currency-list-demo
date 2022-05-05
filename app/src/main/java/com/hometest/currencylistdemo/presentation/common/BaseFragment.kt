package com.hometest.currencylistdemo.presentation.common

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AppLog

abstract class BaseFragment : Fragment() {
    protected fun showToast(message: String?) {
        if (activity == null) return
        Toast.makeText(activity, message, Toast.LENGTH_SHORT)
            .show()
    }

    protected fun showLongToast(message: String?) {
        if (activity == null) return
        Toast.makeText(activity, message, Toast.LENGTH_LONG)
            .show()
    }

    private var mProgressDialog: ProgressDialog? = null

    protected fun onShowLoading() {
        if (mProgressDialog == null) mProgressDialog =
            ProgressDialog.show(activity, "", getString(R.string.loading))
        mProgressDialog!!.show()
    }

    protected fun onHideLoading() {
        if (mProgressDialog == null) return
        mProgressDialog!!.dismiss()
    }

    fun onShowKeyBoard(view: View) {
        AppLog.d(AppConstants.TAG, "showKeyBoard")
        if (activity == null || !isAdded) return
        view.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun onHideKeyBoard(view: View?) {
        AppLog.d(AppConstants.TAG, "onHideKeyBoard")
        if (activity == null || !isAdded) return
        var currentFocus = requireActivity().currentFocus
        if (currentFocus == null) currentFocus = view
        currentFocus!!.clearFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    protected open fun showAlert(
        context: Context?,
        message: CharSequence?,
        okActionListener: DialogInterface.OnClickListener? = null,
        cancelActionListener: DialogInterface.OnClickListener? = null
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(resources.getText(R.string.info)).setMessage(message).setCancelable(false)
        if (okActionListener != null) {
            builder.setPositiveButton(android.R.string.ok, okActionListener)
        } else {
            builder.setPositiveButton(android.R.string.ok) { _, _ -> }
        }
        if (cancelActionListener != null) {
            builder.setNegativeButton(R.string.cancel, cancelActionListener)
        }
        builder.show()
    }
}