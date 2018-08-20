package com.mumsapp.android.shop

import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.ShopProductsMapper
import com.mumsapp.domain.interactor.shop.AddProductToFavouriteUseCase
import com.mumsapp.domain.interactor.shop.RemoveProductFromFavouriteUseCase
import com.mumsapp.domain.interactor.shop.SearchShopProductsUseCase
import com.mumsapp.domain.model.shop.ShopProductIdRequest
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.model.shop.ProductsResponse
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
    private val shopProductsMapper: ShopProductsMapper
    private val addProductToFavouriteUseCase: AddProductToFavouriteUseCase
    private val removeProductFromFavouriteUseCase: RemoveProductFromFavouriteUseCase

    private var searchTerm: String? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                shopFiltersManager: ShopFiltersManager,
                resourceRepository: ResourceRepository,
                searchShopProductsUseCase: SearchShopProductsUseCase,
                sessionManager: SessionManager,
                shopProductsMapper: ShopProductsMapper,
                addProductToFavouriteUseCase: AddProductToFavouriteUseCase,
                removeProductFromFavouriteUseCase: RemoveProductFromFavouriteUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.shopFiltersManager = shopFiltersManager
        this.resourceRepository = resourceRepository
        this.searchShopProductsUseCase = searchShopProductsUseCase
        this.sessionManager = sessionManager
        this.shopProductsMapper = shopProductsMapper
        this.addProductToFavouriteUseCase = addProductToFavouriteUseCase
        this.removeProductFromFavouriteUseCase = removeProductFromFavouriteUseCase
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
        searchTerm = value
        loadItems()
    }

    private fun loadItems() {
        val minPrice = when {
            shopFiltersManager.getGiveItForFree() == true -> 0
            shopFiltersManager.getMinPrice() == null -> DEFAULT_MIN_PRICE
            else -> shopFiltersManager.getMinPrice()
        }

        val maxPrice = when {
            shopFiltersManager.getGiveItForFree() == true -> 0
            shopFiltersManager.getMaxPrice() == null -> DEFAULT_MAX_PRICE
            else -> shopFiltersManager.getMaxPrice()
        }

        val minDistance = if (shopFiltersManager.getMinDistance() == null) {
            DEFAULT_MIN_DISTANCE
        } else {
            shopFiltersManager.getMinDistance()
        }

        val maxDistance = if (shopFiltersManager.getMaxDistance() == null) {
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

        val readableCategory = category?.name
                ?: resourceRepository.getString(R.string.all_categories)

        val readablePrice = resourceRepository.getString(R.string.shop_filter_price_pattern, priceMin, priceMax)

        val readableDistance = resourceRepository.getString(R.string.shop_filter_distance_pattern, distanceMin, distanceMax)

        view?.showFilterValues(readableCategory, readablePrice, readableDistance)
    }

    private fun loadProducts(page: Int, perPage: Int, searchTerms: String?, categoryId: Int?,
                             minPrice: Float?, maxPrice: Float?, userLat: Double?, userLon: Double?,
                             minDistance: Int?, maxDistance: Int?) {
        val request = SearchShopRequest(page, perPage, searchTerms, categoryId, minPrice, maxPrice,
                userLat, userLon, minDistance, maxDistance)

        addDisposable(
                searchShopProductsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe({ handleLoadProductsSuccess(it, userLat, userLon) }, this::handleApiError)
        )
    }

    private fun handleLoadProductsSuccess(response: ProductsResponse, userLat: Double?, userLon: Double?) {
        val products = shopProductsMapper.map(response.data.products, userLat, userLon)
        view?.showItems(products, this::onProductClick, this::onFavouriteCheckboxChanged)
    }

    private fun onProductClick(product: ReadableShopProduct) {
        fragmentsNavigationService.openProductFragment(product.id, true)
    }

    private fun onFavouriteCheckboxChanged(item: ReadableShopProduct, value: Boolean) {
        val request = ShopProductIdRequest(item.id)
        if(value) {
            addProductToFavourite(request)
        } else {
            removeProductFromFavourite(request)
        }
    }

    private fun addProductToFavourite(request: ShopProductIdRequest) {
        addDisposable(
                addProductToFavouriteUseCase.execute(request)
                        .subscribe(this::handleProductFavouriteSuccess, this::handleApiError)
        )
    }

    private fun removeProductFromFavourite(request: ShopProductIdRequest) {
        addDisposable(
                removeProductFromFavouriteUseCase.execute(request)
                        .subscribe(this::handleProductFavouriteSuccess, this::handleApiError)
        )
    }

    private fun handleProductFavouriteSuccess(response: EmptyResponse) {
        //ignored
    }
}