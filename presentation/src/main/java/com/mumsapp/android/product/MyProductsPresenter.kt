package com.mumsapp.android.product

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class MyProductsPresenter : LifecyclePresenter<MyProductsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }
}