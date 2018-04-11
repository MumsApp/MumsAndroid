package com.mumsapp.android.organisation

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class OrganisationDetailsPresenter : LifecyclePresenter<OrganisationDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    lateinit var organisationId: String

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(organisationId: String) {
        this.organisationId = organisationId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }
}