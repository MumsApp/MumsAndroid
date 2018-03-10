package com.mumsapp.android.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mumsapp.android.common.features.HasComponent

abstract class BaseDialog(context: Context) : Dialog(context), BaseView {

    init {
        if(context is BaseActivity) {
            ownerActivity = context
        }
    }

    protected fun configureWindow() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast((ownerActivity as HasComponent<C>).getComponent())
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

    override fun isSessionExpiredPresented() = false

    override fun showSessionExpired() {}
}