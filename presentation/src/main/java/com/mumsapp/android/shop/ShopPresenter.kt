package com.mumsapp.android.shop

import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.SearchShopProductsUseCase
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.SearchShopRequest
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.*
import javax.inject.Inject

class ShopPresenter : LifecyclePresenter<ShopView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val shopFiltersManager: ShopFiltersManager
    private val resourceRepository: ResourceRepository
    private val searchShopProductsUseCase: SearchShopProductsUseCase
    private val sessionManager: SessionManager

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                shopFiltersManager: ShopFiltersManager,
                resourceRepository: ResourceRepository,
                searchShopProductsUseCase: SearchShopProductsUseCase,
                sessionManager: SessionManager) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.shopFiltersManager = shopFiltersManager
        this.resourceRepository = resourceRepository
        this.searchShopProductsUseCase = searchShopProductsUseCase
        this.sessionManager = sessionManager
    }

    override fun create() {
        shopFiltersManager.clear()
        loadItems(null)
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
        loadItems(value)
    }

    fun onProductClick(product: Product) {
        fragmentsNavigationService.openProductFragment(product.id, true)
    }

    private fun loadItems(searchTerm: String?) {
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

        val place: Place? = shopFiltersManager.getPlace()
        val user = sessionManager.loadLoggedUser()

        val userLat = place?.latLng?.latitude ?: user?.data?.location?.latitude?.toDouble()
        val userLon = place?.latLng?.longitude ?: user?.data?.location?.longitude?.toDouble()

        setFilterValues(shopFiltersManager.getSubcategory(), minPrice!!, maxPrice!!, minDistance!!, maxDistance!!)

        loadProducts(1, Int.MAX_VALUE, searchTerm,
                shopFiltersManager.getSubcategory()?.id, minPrice.toFloat(), maxPrice.toFloat(),
                userLat, userLon, minDistance,
                maxDistance)
    }

    private fun setFilterValues(category: ProductSubcategory?, priceMin: Int, priceMax: Int,
                                distanceMin: Int, distanceMax: Int) {

        val readableCategory = category?.name ?: resourceRepository.getString(R.string.all_categories)

        val readablePrice = resourceRepository.getString(R.string.shop_filter_price_pattern, priceMin, priceMax)

        val readableDistance = resourceRepository.getString(R.string.shop_filter_distance_pattern, distanceMin, distanceMax)

        view?.showFilterValues(readableCategory, readablePrice, readableDistance)
    }

    private fun loadProducts(page: Int, perPage: Int, searchTerms: String?, categoryId: Int?, minPrice: Float?, maxPrice: Float?, userLat: Double?, userLon: Double?, minDistance: Int?, maxDistance: Int?) {
        val request = SearchShopRequest(page, perPage, searchTerms, categoryId, minPrice, maxPrice,
                userLat, userLon, minDistance, maxDistance)

        addDisposable(
                searchShopProductsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductsSuccess, this::handleApiError)
        )
    }

    private fun handleLoadProductsSuccess(response: ProductResponse) {
        view?.showItems(response.data.products, this::onFavouriteCheckboxChanged)
    }

    private fun onFavouriteCheckboxChanged(item: Product, value: Boolean) {

    }
}