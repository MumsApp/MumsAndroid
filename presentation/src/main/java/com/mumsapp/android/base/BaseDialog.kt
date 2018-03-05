package com.mumsapp.android.base

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import butterknife.Unbinder
import com.mumsapp.android.MainApplication
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.AppComponent

abstract class BaseDialog(context: Context) : AlertDialog(context), BaseView {

    private var unbinder: Unbinder? = null

    protected fun setUnbinder(unbinder: Unbinder) {
        this.unbinder = unbinder
    }

    protected fun getComponent(): AppComponent {
        return MainApplication.getApplication(context.applicationContext).appComponent
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        unbinder?.unbind()
    }


    override fun hideKeyboard() {
        if(context is BaseActivity) {
            (context as BaseActivity).hideKeyboard()
        }
    }

    override fun showError(error: String) {
        if(context is BaseActivity) {
            (context as BaseActivity).showError(error)
        }
    }

    override fun showToast(text: String) {
        if(context is BaseActivity) {
            (context as BaseActivity).showToast(text)
        }
    }

    override fun showToast(text: String, length: Int) {
        if(context is BaseActivity) {
            (context as BaseActivity).showToast(text, length)
        }
    }

    override fun showSnackbar(message: String) {
        if(context is BaseActivity) {
            (context as BaseActivity).showSnackbar(message)
        }
    }

    override fun showSnackbarSticky(message: String, text: CharSequence, clickListener: View.OnClickListener) {
        if(context is BaseActivity) {
            (context as BaseActivity).showSnackbarSticky(message, text, clickListener)
        }
    }

    override fun showLoading() {

    }

    override fun hideOverlays() {

    }

    override fun isLoadingPresented(): Boolean = false
}