package com.mumsapp.android.util

import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardHelper(private val imm: InputMethodManager) {

    fun hideKeyboard(view: View?) = imm.hideSoftInputFromWindow(view?.windowToken, 0)
}