package com.mumsapp.android.product

import com.google.android.gms.location.places.Place
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.ShopFiltersManager
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class EditProductPresenter : BaseProductFormPresenter<EditProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private val resourceRepository: ResourceRepository
    private val shopFiltersManager: ShopFiltersManager
    private val validationHelper: ValidationHelper

    private lateinit var currentProduct: ReadableShopProduct

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository,
                shopFiltersManager: ShopFiltersManager,
                validationHelper: ValidationHelper): super(fragmentsNavigationService, filesHelper,
            resourceRepository, shopFiltersManager, validationHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
        this.resourceRepository = resourceRepository
        this.shopFiltersManager = shopFiltersManager
        this.validationHelper = validationHelper
    }

    fun setArguments(product: ReadableShopProduct) {
        currentProduct = product
    }

    override fun saveProduct(photos: MutableList<ImageSliderItem>, title: String,
                             category: ProductSubcategory, price: String, description: String,
                             location: Place) {
    }
}