package com.mumsapp.android.authentication

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class AuthMenuPresenter: BasePresenter<AuthMenuView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onSignUpClick() {
        fragmentsNavigationService.openSignUpFragment(true)
    }

    fun onSignInClick() {
        fragmentsNavigationService.openSignInFragment(true)
    }

    fun onCreatePageClick() {
        fragmentsNavigationService.openCreatePageFragment(true)
    }
}