package com.mumsapp.android.base

import android.view.View
import com.mumsapp.android.common.features.HasOverlays

interface BaseView : HasOverlays {

    fun <T: BasePresenter<BaseView>> getPresenter(): T

    fun hideKeyboard()

    fun showError(error: String)

    fun showToast(text: String)

    fun showToast(text: String, length: Int)

    fun showSnackbar(message: String)

    fun showSnackbarSticky(message: String, text: CharSequence, clickListener: View.OnClickListener)
}