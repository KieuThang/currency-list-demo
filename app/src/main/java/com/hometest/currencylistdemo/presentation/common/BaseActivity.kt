package com.hometest.currencylistdemo.presentation.common

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.Window
import android.view.WindowManager.BadTokenException
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hometest.currencylistdemo.R
import com.hometest.currencylistdemo.common.AppConstants
import com.hometest.currencylistdemo.common.AppLog

abstract class BaseActivity : AppCompatActivity() {

    fun showToastMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private var mProgressDialog: ProgressDialog? = null

    protected open fun showLoading() {
        AppLog.d(AppConstants.TAG, "show loading")
        try {
            if (mProgressDialog == null) mProgressDialog = ProgressDialog.show(this, "", getString(R.string.loading))
            mProgressDialog!!.show()
        } catch (e: Exception) {
            AppLog.d(AppConstants.TAG, "show loading error: ${e.message}")
        }
    }

    fun hideLoading() {
        AppLog.d(AppConstants.TAG, "hide loading")
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected open fun hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    @Throws(BadTokenException::class)
    open fun showProgress(title: String?, message: String?, theme: Int): ProgressDialog? {
        if (mProgressDialog == null) {
            mProgressDialog = if (theme > 0) ProgressDialog(this, theme) else ProgressDialog(this)
            mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            mProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.isIndeterminate = true
        }
        if (!title.isNullOrBlank()) mProgressDialog!!.setTitle(title)
        mProgressDialog!!.setMessage(message)
        return mProgressDialog
    }

    fun onShowKeyBoard(view: View) {
        AppLog.d(AppConstants.TAG, "showKeyBoard")
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun onHideKeyBoard(view: View?) {
        AppLog.d(AppConstants.TAG, "onHideKeyBoard")
        var currentFocus = currentFocus
        if (currentFocus == null) currentFocus = view
        currentFocus!!.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    protected open fun showAlert(
        context: Context?,
        message: CharSequence?,
        okActionListener: DialogInterface.OnClickListener? = null,
        cancelActionListener: DialogInterface.OnClickListener? = null
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getText(R.string.info)).setMessage(message).setCancelable(false)
        if (okActionListener != null) {
            builder.setPositiveButton(android.R.string.ok, okActionListener)
        } else {
            builder.setPositiveButton(android.R.string.ok) { _, _ -> }
        }
        if(cancelActionListener != null){
            builder.setNegativeButton(R.string.cancel, cancelActionListener)
        }
        builder.show()
    }
}