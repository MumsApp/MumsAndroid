package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import javax.inject.Inject

class ShopPresenter : LifecyclePresenter<ShopView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onMenuButtonClick() {
        view?.openMenuDialog()
    }

    fun onFilterClick() {
        fragmentsNavigationService.openShopFilterFragment(true)
    }

    fun onDialogSearchButtonClick() {
        view?.closeMenuDialog()
        view?.startSearching()
    }

    fun onSearch(value: String) {

    }
}