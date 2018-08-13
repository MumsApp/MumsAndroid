package com.mumsapp.android.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.Unbinder
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.common.features.HasOverlays

abstract class BaseFragment : Fragment(), LifecycleView {

    private var unbinder: Unbinder? = null

    private var retainedChildFragmentManager: FragmentManager? = null

    protected fun setUnbinder(unbinder: Unbinder) {
        this.unbinder = unbinder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(bottomMenuVisibile()) {
            showBottomMenuButton()
        } else {
            hideBottomMenuButton()
        }
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

    fun goingBack(): Boolean = getLifecyclePresenter<LifecyclePresenter<LifecycleView>>() != null &&
            getLifecyclePresenter<LifecyclePresenter<LifecycleView>>().onGoingBack()

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

    open fun bottomMenuVisibile() = true

    override fun hideBottomMenuButton() {
        if(activity is LifecycleView) {
            (activity as LifecycleView).hideBottomMenuButton()
        }
    }

    override fun showBottomMenuButton() {
        if(activity is LifecycleView) {
            (activity as LifecycleView).showBottomMenuButton()
        }
    }

    override fun isBottomMenuButtonVisible(): Boolean {
        if(activity is LifecycleView) {
            return (activity as LifecycleView).isBottomMenuButtonVisible()
        }

        return false
    }

    override fun askForPermissions(onGrantedCallback: () -> Unit, onDeniedCallback: (permissions: List<String>) -> Unit, vararg permissions: String) {
        (activity as BaseActivity).askForPermissions(onGrantedCallback, onDeniedCallback, *permissions)
    }

    protected fun safeChildFragmentManager(): FragmentManager {
        if (retainedChildFragmentManager == null) {
            retainedChildFragmentManager = childFragmentManager
        } else {
        }
        return retainedChildFragmentManager!!
    }

    protected fun setVisibilistyFromBoolean(visible: Boolean, view: View) {
        val visiblity = if(visible) {
            View.VISIBLE
        } else {
            View.GONE
        }

        view.visibility = visiblity
    }
}