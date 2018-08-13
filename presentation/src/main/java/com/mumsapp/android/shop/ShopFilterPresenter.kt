package com.mumsapp.android.shop

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ShopFiltersManager
import javax.inject.Inject

class ShopFilterPresenter : LifecyclePresenter<ShopFilterView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val shopFiltersManager: ShopFiltersManager
    private val resourceRepository: ResourceRepository

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                shopFiltersManager: ShopFiltersManager, resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.shopFiltersManager = shopFiltersManager
        this.resourceRepository = resourceRepository
    }

    override fun start() {
        setFilterValues()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onSelectCategoryClick() {
        fragmentsNavigationService.openSelectCategoryFragment(true)
    }

    fun onGiveItToFreeCheckedChanged(value: Boolean) {
        if(value) {
            view?.disablePriceSelection()
        } else {
            view?.enablePriceSelection()
        }
    }

    private fun setFilterValues() {
        if(shopFiltersManager.getSubcategoryName() == null) {
            val hint = resourceRepository.getString(R.string.select_product_category)
            view?.setCategoryName(hint)
        } else {
            view?.setCategoryName(shopFiltersManager.getSubcategoryName())
        }
    }
}