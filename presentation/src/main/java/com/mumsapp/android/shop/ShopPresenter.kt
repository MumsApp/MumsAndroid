package com.mumsapp.android.shop

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.DEFAULT_MAX_DISTANCE
import com.mumsapp.android.util.DEFAULT_MAX_PRICE
import com.mumsapp.android.util.DEFAULT_MIN_DISTANCE
import com.mumsapp.android.util.DEFAULT_MIN_PRICE
import com.mumsapp.domain.interactor.shop.GetShopItemsUseCase
import com.mumsapp.domain.interactor.shop.SearchShopProductsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.SearchShopRequest
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ShopFiltersManager
import javax.inject.Inject

class ShopPresenter : LifecyclePresenter<ShopView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val shopFiltersManager: ShopFiltersManager
    private val resourceRepository: ResourceRepository
    private val searchShopProductsUseCase: SearchShopProductsUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                shopFiltersManager: ShopFiltersManager,
                resourceRepository: ResourceRepository,
                searchShopProductsUseCase: SearchShopProductsUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.shopFiltersManager = shopFiltersManager
        this.resourceRepository = resourceRepository
        this.searchShopProductsUseCase = searchShopProductsUseCase
    }

    override fun create() {
        shopFiltersManager.clear()
    }

    override fun start() {
        loadItems()
    }

    fun onAddClick() {
        fragmentsNavigationService.openAddProductFragment(true)
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

    fun onProductClick(product: Product) {
        fragmentsNavigationService.openProductFragment(product.id, true)
    }

    private fun loadItems() {
        val minPrice = if(shopFiltersManager.getMinPrice() == null) {
            DEFAULT_MIN_PRICE
        } else {
            shopFiltersManager.getMinPrice()
        }

        val maxPrice = if(shopFiltersManager.getMaxPrice() == null) {
            DEFAULT_MAX_PRICE
        } else {
            shopFiltersManager.getMaxPrice()
        }

        val minDistance = if(shopFiltersManager.getMinDistance() == null) {
            DEFAULT_MIN_DISTANCE
        } else {
            shopFiltersManager.getMinDistance()
        }

        val maxDistance = if(shopFiltersManager.getMaxDistance() == null) {
            DEFAULT_MAX_DISTANCE
        } else {
            shopFiltersManager.getMaxDistance()
        }

        setFilterValues(shopFiltersManager.getSubcategory(), minPrice!!, maxPrice!!, minDistance!!, maxDistance!!)

        val request = SearchShopRequest()
        addDisposable(
                searchShopProductsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess)
        )
    }

    private fun setFilterValues(category: ProductSubcategory?, priceMin: Int, priceMax: Int,
                                distanceMin: Int, distanceMax: Int) {

        val readableCategory = category?.name ?: resourceRepository.getString(R.string.all_categories)

        val readablePrice = resourceRepository.getString(R.string.shop_filter_price_pattern, priceMin, priceMax)

        val readableDistance = resourceRepository.getString(R.string.shop_filter_distance_pattern, distanceMin, distanceMax)

        view?.showFilterValues(readableCategory, readablePrice, readableDistance)
    }

    private fun handleLoadProductsSuccess(response: ProductResponse) {
        view?.showItems(response.items, this::onFavouriteCheckboxChanged)
    }

    private fun onFavouriteCheckboxChanged(item: Product, value: Boolean) {

    }
}