package com.mumsapp.android.product

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class ProductPresenter : LifecyclePresenter<ProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    private var productId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun setArguments(productId: Int?) {
        this.productId = productId
    }
}