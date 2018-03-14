package com.mumsapp.android.base

import android.support.v4.app.Fragment
import android.view.View
import butterknife.Unbinder
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.common.features.HasOverlays

abstract class BaseFragment : Fragment(), LifecycleView {

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

    fun goingBack(): Boolean = getLifecyclePresenter<LifecyclePresenter<LifecycleView>>() != null || getLifecyclePresenter<LifecyclePresenter<LifecycleView>>().onGoingBack()

    override fun showLoading() {
        if(activity is HasOverlays) {
            (activity as HasOverlays).showLoading()
            return
        }

        throw IllegalStateException("Activity must be HasOverlays")
    }

    override fun hideOverlays() {
        if(activity is HasOverlays) {
            (activity as HasOverlays).hideOverlays()
            return
        }

        throw IllegalStateException("Activity must be HasOverlays")
    }

    override fun isLoadingPresented(): Boolean {
        if(activity is HasOverlays) {
            return (activity as HasOverlays).isLoadingPresented()
        }

        throw IllegalStateException("Activity must be HasOverlays")
    }

    override fun isSessionExpiredPresented(): Boolean {
        if(activity is HasOverlays) {
            return (activity as HasOverlays).isSessionExpiredPresented()
        }

        return false
    }

    override fun showSessionExpired() {
        if(activity is HasOverlays) {
            (activity as HasOverlays).showSessionExpired()
        }
    }
}