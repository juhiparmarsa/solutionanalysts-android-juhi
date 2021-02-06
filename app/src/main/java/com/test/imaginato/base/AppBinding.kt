package com.test.imaginato.base

import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

import androidx.databinding.BindingAdapter

class AppBinding {

    companion object {

        @BindingAdapter("setErrorMessage")
        @JvmStatic
        fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
            errorMessage?.let {
                view.error = it
                view.isErrorEnabled = !errorMessage.isNullOrEmpty()
            }
        }

        @JvmStatic
        @BindingAdapter("app:onEditorAction")
        fun onEditorAction(editText: EditText, listener: TextView.OnEditorActionListener) {
            editText.setOnEditorActionListener(listener)
        }

    }


}