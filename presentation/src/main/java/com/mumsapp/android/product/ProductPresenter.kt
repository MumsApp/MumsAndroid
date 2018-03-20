package com.mumsapp.android.product

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.repository.ResourceRepository
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.DrawableBanner
import javax.inject.Inject

class ProductPresenter : LifecyclePresenter<ProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository

    private var productId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
    }

    override fun create() {
        showMockedData()
    }

    fun setArguments(productId: Int?) {
        this.productId = productId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onContactClick() {

    }

    private fun showMockedData() {
        val banners = ArrayList<Banner>()
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        banners.add(DrawableBanner(R.drawable.ic_image_placeholder))
        val price = resourceRepository.getString(R.string.pounds_price_format, 55)
        val description = resourceRepository.getString(R.string.template_long_text)

        view?.setProductDetails(banners, "Item one", "Category", "",
                "Layla P.", price, "3 miles", description)

        view?.setLocationDetails("London", 51.509865, -0.118092)
    }

}