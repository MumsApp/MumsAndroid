package com.mumsapp.android.product

import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.CreateShopProductUseCase
import com.mumsapp.domain.model.shop.CreateShopProductRequest
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.ShopFiltersManager
import com.mumsapp.domain.utils.ValidationHelper
import java.io.File
import javax.inject.Inject

class AddProductPresenter : BaseProductFormPresenter<AddProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val createShopProductsUseCase: CreateShopProductUseCase
    private val shopFiltersManager: ShopFiltersManager

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository,
                createShopProductsUseCase: CreateShopProductUseCase,
                shopFiltersManager: ShopFiltersManager,
                validationHelper: ValidationHelper): super(fragmentsNavigationService, filesHelper,
            resourceRepository, shopFiltersManager, validationHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.createShopProductsUseCase = createShopProductsUseCase
        this.shopFiltersManager = shopFiltersManager
    }

    override fun saveProduct(photos: MutableList<ImageSliderItem>, title: String,
                            category: ProductSubcategory, price: String,
                            description: String) {
        val location = selectedLocation!!

        val photoFiles = getPhotoFilesWithCorrectOrder(photos)

        val request = CreateShopProductRequest(title, description, price.toFloat(), category.id,
                location.latLng.latitude, location.latLng.longitude, location.name.toString(),
                photoFiles)

        addDisposable(
                createShopProductsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSaveProductSuccess, this::handleApiError)
        )
    }

    private fun getPhotoFilesWithCorrectOrder(photos: MutableList<ImageSliderItem>): List<File> {
        val list = ArrayList<File>()
        list.add(currentHeader!!.file!!)

        photos.remove(currentHeader!!)

        photos.forEach {
            if(!it.isAddPhoto) {
                list.add(it.file!!)
            }
        }

        return list
    }

    private fun handleSaveProductSuccess(response: ProductResponse) {
        shopFiltersManager.clear()

        val product = response.product

        val title = resourceRepository.getString(R.string.congratulations_your_product_has_been_uploaded)
        val confirmButtonText = resourceRepository.getString(R.string.back_to_search)
        val cancelButtonText = resourceRepository.getString(R.string.to_my_product_list)

        view?.showConfirmationDialog(product.photos!![0].photoPath, "", title, null,
                confirmButtonText, this::onBackToSearchClick, cancelButtonText, this::onBackToMyProductsClick)
    }
}