package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class SelectProductCategoryPresenter : LifecyclePresenter<SelectProductCategoryView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) : super() {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onBackClick() {

    }
}