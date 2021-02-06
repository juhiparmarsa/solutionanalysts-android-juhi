package com.test.imaginato.utils


import android.app.Dialog
import android.content.Context
import com.test.imaginato.R

/*
* Progress indicator use duaring api call
* */
object ProgressUtils {

    private var progressDialog: Dialog? = null

    val isShowing: Boolean
        get() = progressDialog != null

    fun showProgressDialog(context: Context?) {
        if (context != null && (progressDialog == null || !progressDialog!!.isShowing)) {
            progressDialog = Dialog(context, R.style.Base_Theme_AppCompat_Dialog)
            progressDialog?.setCancelable(false)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.show()
            progressDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog?.setContentView(R.layout.layout_progress
            )
        }
    }

    fun forceShowProgressDialog(context: Context?) {
        if (context != null) {
            progressDialog = Dialog(context)
            progressDialog?.setCancelable(true)
            progressDialog?.setContentView(R.layout.layout_progress)
            progressDialog?.show()
        }

    }

    fun closeProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            progressDialog = null
        }
    }

}