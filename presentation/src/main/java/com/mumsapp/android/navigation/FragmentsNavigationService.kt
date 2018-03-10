package com.mumsapp.android.navigation

import android.support.v4.app.FragmentManager
import com.mumsapp.android.authentication.AuthMenuFragment
import com.mumsapp.android.authentication.CreatePageFragment
import com.mumsapp.android.authentication.SignInFragment
import com.mumsapp.android.authentication.SignUpFragment
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.di.qualifiers.FragmentContainerId
import com.mumsapp.android.profile.MyProfileFragment
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
        val fragment = createAuthMenuFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createAuthMenuFragment() = AuthMenuFragment.getInstance()

    fun openSignUpFragment(addToBackStack: Boolean) {
        val fragment = createSignUpFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createSignUpFragment() = SignUpFragment.getInstance()

    fun openSignInFragment(addToBackStack: Boolean) {
        val fragment = createSignInFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createSignInFragment() = SignInFragment.getInstance()

    fun openCreatePageFragment(addToBackStack: Boolean) {
        val fragment = createCreatePageFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createCreatePageFragment() = CreatePageFragment.getInstance()

    fun openForgetPasswordFragment(addToBackStack: Boolean) {

    }

    fun openMyProfileFragment(addToBackStack: Boolean) {
        val fragment = createMyProfileFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createMyProfileFragment() = MyProfileFragment.getInstance()

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
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, 0)

        val tag = fragment.javaClass.simpleName
        transaction.replace(containerId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
    }

    @Throws(UnsupportedOperationException::class)
    fun findTopFragment(): BaseFragment? {
        val f = fragmentManager.findFragmentById(containerId)
        return if (f != null) {
            f as? BaseFragment ?: throw UnsupportedOperationException("Invalid fragment in fragment manager: Every fragment on the stack should be a child of BaseFragment!")
        } else null

    }
}