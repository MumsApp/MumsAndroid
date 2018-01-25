package com.mumsapp.android.base

import android.os.Bundle
import android.support.annotation.MainThread
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mumsapp.android.MainApplication
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.util.KeyboardHelper
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(), HasComponent<ActivityComponent> {

    protected lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var keyboardHelper: KeyboardHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createActivityComponent()
        makeInject()
    }

    private fun createActivityComponent() {
        activityComponent = MainApplication.getApplication(this).createActivityComponent(this)
    }

    abstract fun makeInject()

    override fun getComponent(): ActivityComponent = activityComponent

    fun hideKeyboard() {
        keyboardHelper.hideKeyboard(this.currentFocus)
    }

    @MainThread
    fun showSnackbar(message: String) {
        showSnackbar(message, Snackbar.LENGTH_LONG)
    }

    @MainThread
    fun showSnackbarSticky(message: String, text: CharSequence, clickListener: View.OnClickListener) {
        showSnackbar(message, Snackbar.LENGTH_INDEFINITE, text, clickListener)
    }

    @MainThread
    private fun showSnackbar(message: String, duration: Int) {
        showSnackbar(message, duration, null, null)
    }

    @MainThread
    private fun showSnackbar(message: String, duration: Int, actionText: CharSequence?,
                             clickListener: View.OnClickListener?) {

        var root = findViewById<View>(android.R.id.content)

        val snackbar = Snackbar.make(root, message, duration)

        if (actionText != null) {
            snackbar.setActionTextColor(resources.getColor(R.color.colorPrimaryDark))
            snackbar.setAction(actionText) { v ->
                snackbar.dismiss()
                clickListener?.onClick(v)
            }
        }
        snackbar.show()
    }

    fun showToast(text: String) = showToast(text, Toast.LENGTH_SHORT)

    fun showToast(text: String, length: Int) = Toast.makeText(this, text, length).show()

    fun showError(error: String) = showSnackbar(error)
}