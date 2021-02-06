package com.test.imaginato.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager?.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(focusView: View?) {
        focusView?.let {
            val imm = focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }

}