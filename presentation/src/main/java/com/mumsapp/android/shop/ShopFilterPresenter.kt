package com.mumsapp.android.shop

import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
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

    private var selectedLocation: Place? = null

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

    override fun onGoingBack(): Boolean {
        saveDetails()

        return super.onGoingBack()
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

    fun onSetLocationClick() {
        view?.showEditLocationScreen()
    }

    fun onLocationSelected(place: Place) {
        handleLocationSuccess(place)
    }

    fun onLocationError(status: Status) {
        view?.showToast(resourceRepository.getString(R.string.location_choose_error))
    }

    private fun setFilterValues() {
        if(shopFiltersManager.getSubcategoryName() == null) {
            val hint = resourceRepository.getString(R.string.select_product_category)
            view?.setCategoryName(hint)
        } else {
            view?.setCategoryName(shopFiltersManager.getSubcategoryName())
        }

        if(shopFiltersManager.getGiveItForFree() != null) {
            view?.setGiveItForFree(shopFiltersManager.getGiveItForFree()!!)

            if(shopFiltersManager.getGiveItForFree()!!) {
                view?.disablePriceSelection()
            }
        }

        if(shopFiltersManager.getMinPrice() != null && shopFiltersManager.getMaxPrice() != null) {
            view?.setPrice(shopFiltersManager.getMinPrice()!!, shopFiltersManager.getMaxPrice()!!)
        }

        if(shopFiltersManager.getMinDistance() != null && shopFiltersManager.getMaxDistance() != null) {
            view?.setDistance(shopFiltersManager.getMinDistance()!!, shopFiltersManager.getMaxDistance()!!)
        }

        if(shopFiltersManager.getPlace() != null) {
            selectedLocation = shopFiltersManager.getPlace()
            val place: Place = shopFiltersManager.getPlace()!!
            view?.showLocationName(place.address.toString())
        }
    }

    private fun handleLocationSuccess(place: Place) {
        selectedLocation = place

        val readableLocation = resourceRepository.getString(R.string.selected_location_pattern, place.address.toString())
        view?.showLocationName(readableLocation)
    }

    private fun saveDetails() {
        val isForFree = view?.getGiveItForFree()
        val minPrice = view?.getMinPrice()
        val maxPrice = view?.getMaxPrice()
        val minDistance = view?.getMinDistance()
        val maxDistance = view?.getMaxDistance()

        shopFiltersManager.setGiveItForFree(isForFree)

        if(isForFree == null || !isForFree) {
            shopFiltersManager.setMinPrice(minPrice)
            shopFiltersManager.setMaxPrice(maxPrice)
        }

        if(selectedLocation != null) {
            shopFiltersManager.setPlace(selectedLocation)
            shopFiltersManager.setMinDistance(minDistance)
            shopFiltersManager.setMaxDistance(maxDistance)
        }

    }
}