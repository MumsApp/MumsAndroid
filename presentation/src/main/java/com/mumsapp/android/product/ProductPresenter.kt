package com.mumsapp.android.product

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.GetShopProductUseCase
import com.mumsapp.domain.model.shop.Product
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.ShopProductIdRequest
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.LocationHelper
import com.mumsapp.domain.utils.SessionManager
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.DrawableBanner
import ss.com.bannerslider.banners.RemoteBanner
import javax.inject.Inject
import kotlin.math.roundToInt

class ProductPresenter : LifecyclePresenter<ProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val getShopProductUseCase: GetShopProductUseCase
    private val locationHelper: LocationHelper
    private val sessionManager: SessionManager
    private val imagesRepository: ImagesRepository

    private var productId: Int = 0

    private var loadedProduct: Product? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository,
                getShopProductUseCase: GetShopProductUseCase,
                locationHelper: LocationHelper,
                sessionManager: SessionManager,
                imagesRepository: ImagesRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.getShopProductUseCase = getShopProductUseCase
        this.locationHelper = locationHelper
        this.sessionManager = sessionManager
        this.imagesRepository = imagesRepository
    }

    override fun start() {
        loadProductDetails()
    }

    fun setArguments(productId: Int) {
        this.productId = productId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onUserClick() {
        openUserProfileScreen()
    }

    fun onContactClick() {
        openUserProfileScreen()
    }

    private fun loadProductDetails() {
        val request = ShopProductIdRequest(productId)

        addDisposable(
                getShopProductUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadProductSuccess, this::handleApiError)
        )
    }

    private fun handleLoadProductSuccess(response: ProductResponse) {
        loadedProduct = response.product
        showProductDetails(loadedProduct!!)
    }

    private fun showProductDetails(product: Product) {
        val images = createImages(product)
        val price = resourceRepository.getString(R.string.pounds_price_format, product.price)
        val distance = getReadableDistance(product)

        view?.setProductDetails(images, product.name, product.categoryName,
                product.creatorPhotoPath, product.creatorName, price, distance, product.description)
        view?.setLocationDetails(product.pointName, product.lat, product.lon)
    }

    private fun createImages(product: Product): ArrayList<Banner> {
        val list = ArrayList<Banner>()

        if(product.photos.orEmpty().isEmpty()) {
            list.add(DrawableBanner(R.drawable.ic_image_placeholder))

            return list
        }

        product.photos?.forEach {
            val url = imagesRepository.getApiImageUrl(it.photoPath)
            val banner = RemoteBanner(url)

            list.add(banner)
        }

        return list
    }

    private fun getReadableDistance(product: Product): String? {
        val user = sessionManager.loadLoggedUser()!!.data
        val userLat = user.location.latitude?.toDouble()
        val userLon = user.location.longitude?.toDouble()

        if(userLat != null && userLon != null) {
            val distance = locationHelper.calculateDistanceInMiles(userLat, userLon, product.lat, product.lon).roundToInt()
            val formattedMile = resourceRepository.getQuantityString(R.plurals.mile_plural, distance.toInt())

            return "$distance $formattedMile"
        }

        return null
    }

    private fun openUserProfileScreen() {
        fragmentsNavigationService.openUserProfileFragment(loadedProduct?.creatorId!!, true)
    }
}