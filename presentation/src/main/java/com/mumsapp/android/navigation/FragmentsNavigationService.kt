package com.mumsapp.android.navigation

import android.support.v4.app.FragmentManager
import com.mumsapp.android.authentication.AuthMenuFragment
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.di.qualifiers.FragmentContainerId
import javax.inject.Inject

class FragmentsNavigationService {

    private val fragmentManager: FragmentManager
    private val containerId: Int

    @Inject
    constructor(fragmentManager: FragmentManager, @FragmentContainerId containerId: Int) {
        this.fragmentManager = fragmentManager
        this.containerId = containerId
    }

    fun openAuthMenuFragment(addToBackStack: Boolean) {
        var fragment = createAuthMenuFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createAuthMenuFragment(): AuthMenuFragment {
        return AuthMenuFragment.getInstance()
    }

    fun openSignUpFragment(addToBackStack: Boolean) {

    }

    fun openSignInFragment(addToBackStack: Boolean) {

    }

    fun openCreatePageFragment(addToBackStack: Boolean) {

    }

    fun popFragment() {
        fragmentManager.popBackStack()
    }

    fun popFragmentsToRoot() {
        if (getBackStackCount() > 0) {
            val entryName = fragmentManager.getBackStackEntryAt(0).name
            fragmentManager.popBackStackImmediate(entryName, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun getBackStackCount(): Int {
        return fragmentManager.backStackEntryCount
    }

    private fun openFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        fragmentManager.executePendingTransactions()
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        val tag = fragment.javaClass.getSimpleName()
        transaction.replace(containerId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }

    @Throws(UnsupportedOperationException::class)
    fun findTopFragment(): BaseFragment? {
        val f = fragmentManager.findFragmentById(containerId)
        return if (f != null) {
            if (f is BaseFragment) {
                f as BaseFragment
            } else {
                throw UnsupportedOperationException("Invalid fragment in fragment manager: Every fragment on the stack should be a child of BaseFragment!")
            }
        } else null

    }
}