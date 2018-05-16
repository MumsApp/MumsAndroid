package com.mumsapp.android.profile

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class UserProfilePresenter : LifecyclePresenter<UserProfileView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onRemoveContactClick() {

    }

    override fun start() {
        showMockedData()
    }

    private fun showMockedData() {
        view?.showProfileInfo("Template name", "Description")
        view?.showNumberOfKids("Mother of one 1 year old.")
    }
}