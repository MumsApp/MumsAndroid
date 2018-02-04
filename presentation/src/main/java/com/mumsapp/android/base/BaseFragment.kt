package com.mumsapp.android.base

import android.support.v4.app.Fragment
import android.view.View
import butterknife.Unbinder
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.common.features.HasProgress

abstract class BaseFragment: Fragment(), BaseView {

    private var unbinder: Unbinder? = null

    protected fun setUnbinder(unbinder: Unbinder) {
        this.unbinder = unbinder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast((activity as HasComponent<C>).getComponent())
    }

    override fun hideKeyboard() = (activity as BaseActivity).hideKeyboard()

    override fun showToast(text: String) = (activity as BaseActivity).showToast(text)

    override fun showToast(text: String, length: Int) = (activity as BaseActivity).showToast(text, length)

    override fun showSnackbar(message: String) = (activity as BaseActivity).showSnackbar(message)

    override fun showSnackbarSticky(message: String, text: CharSequence, clickListener: View.OnClickListener) {
        (activity as BaseActivity).showSnackbarSticky(message, text, clickListener)
    }

    override fun showError(error: String) = (activity as BaseActivity).showError(error)

    fun goingBack(): Boolean = getPresenter<BasePresenter<BaseView>>() != null || getPresenter<BasePresenter<BaseView>>().onGoingBack()

    protected fun showProgress() {
        if(activity is HasProgress) {
            (activity as HasProgress).showProgress()
            return
        }

        throw IllegalStateException("Activity must be HasProgress")
    }

    protected fun hideProgress() {
        if(activity is HasProgress) {
            (activity as HasProgress).hideProgress()
            return
        }

        throw IllegalStateException("Activity must be HasProgress")
    }
}