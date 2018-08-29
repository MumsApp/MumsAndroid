package com.mumsapp.android.product

import com.google.android.gms.maps.model.LatLng
import com.mumsapp.android.R
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.shop.ReadableShopProduct
import com.mumsapp.domain.interactor.shop.ChangeProductThumbnailUseCase
import com.mumsapp.domain.interactor.shop.DeleteProductPhotoUseCase
import com.mumsapp.domain.interactor.shop.UpdateShopProductUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.shop.ProductPhotoIdRequest
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.model.shop.UpdateShopProductRequest
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.ShopFiltersManager
import com.mumsapp.domain.utils.ValidationHelper
import java.io.File
import javax.inject.Inject

class EditProductPresenter : BaseProductFormPresenter<EditProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private val resourceRepository: ResourceRepository
    private val shopFiltersManager: ShopFiltersManager
    private val validationHelper: ValidationHelper
    private val updateShopProductUseCase: UpdateShopProductUseCase
    private val deleteProductPhotoUseCase: DeleteProductPhotoUseCase
    private val changeProductThumbnailUseCase: ChangeProductThumbnailUseCase

    private lateinit var currentProduct: ReadableShopProduct

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository,
                shopFiltersManager: ShopFiltersManager,
                validationHelper: ValidationHelper,
                updateShopProductUseCase: UpdateShopProductUseCase,
                deleteProductPhotoUseCase: DeleteProductPhotoUseCase,
                changeProductThumbnailUseCase: ChangeProductThumbnailUseCase): super(fragmentsNavigationService, filesHelper,
            resourceRepository, shopFiltersManager, validationHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
        this.resourceRepository = resourceRepository
        this.shopFiltersManager = shopFiltersManager
        this.validationHelper = validationHelper
        this.updateShopProductUseCase = updateShopProductUseCase
        this.deleteProductPhotoUseCase = deleteProductPhotoUseCase
        this.changeProductThumbnailUseCase = changeProductThumbnailUseCase
    }

    fun setArguments(product: ReadableShopProduct) {
        currentProduct = product
    }

    override fun create() {
        super.create()

        view?.showInitialDetails(currentProduct.name, currentProduct.categoryName,
                currentProduct.product.price.toString(), currentProduct.product.description)

        if(currentProduct.thumbnailPath != null) {
            currentHeader = ImageSliderItem(null, null, false, currentProduct.thumbnailPath)
            view?.showImageHeader(currentProduct.thumbnailPath!!)
        }

        view?.showNewLocation(currentProduct.product.lat, currentProduct.product.lon,
                currentProduct.product.pointName.orEmpty())

        currentProduct.product.photos?.forEach {
            addImageSliderItem(ImageSliderItem(null, null, false, it.photoPath))
        }
    }

    override fun start() {
        super.start()

        val title = resourceRepository.getString(R.string.edit_product)
        view?.setScreenTitle(title)
    }

    override fun showCorrectCategory() {
        val category = if(shopFiltersManager.getSubcategory() == null) {
            currentProduct.categoryName
        } else {
            shopFiltersManager.getSubcategory()!!.name
        }

        view?.showProductCategory(category)
    }

    override fun getSelectedCategory(): ProductSubcategory? {
        return if(shopFiltersManager.getSubcategory() == null) {
            ProductSubcategory(currentProduct.product.categoryId,
                    currentProduct.product.categoryName)
        } else {
            shopFiltersManager.getSubcategory()
        }
    }

    override fun onPhotoSliderItemClick(item: ImageSliderItem) {
        val position = getImageSliderPosition(item)

        if(position != null && item.apiUrl != null) {
            setProductThumbnailOnServer(currentProduct.id, currentProduct.product.photos!![position - 1].id)
        }

        super.onPhotoSliderItemClick(item)
    }

    private fun getImageSliderPosition(item: ImageSliderItem): Int? {
        chosenPhotos.forEachIndexed { index:Int, currentItem: ImageSliderItem ->
            if(item == currentItem) {
                return index
            }
        }

        return null
    }

    private fun setProductThumbnailOnServer(productId: Int, photoId: Int) {
        val request = ProductPhotoIdRequest(productId, photoId)
        addDisposable(changeProductThumbnailUseCase.execute(request)
                .subscribe(this::handleProductThumbnailChange, this::handleApiError)
        )
    }

    private fun handleProductThumbnailChange(response: EmptyResponse) {
        //Ignoring
    }

    override fun onPhotoRemoved(position: Int) {
        if(chosenPhotos[position].file == null) {
            removePhotoOnServer(currentProduct.id, currentProduct.product.photos!![position - 1].id, position)
        } else {
            super.onPhotoRemoved(position)
        }
    }

    private fun removePhotoOnServer(productId: Int, photoId: Int, position: Int) {
        val request = ProductPhotoIdRequest(productId, photoId)

        addDisposable(deleteProductPhotoUseCase.execute(request)
                .compose(applyOverlaysToObservable())
                .subscribe {handleRemovingPhotoSuccess(position)}
        )
    }

    private fun handleRemovingPhotoSuccess(position: Int) {
        super.onPhotoRemoved(position)
    }

    override fun validateLocation(): Boolean {
        if(selectedLocation != null) {
            return super.validateLocation()
        }


         return validationHelper.checkIsNotEmpty(currentProduct.product.pointName)
    }

    override fun saveProduct(photos: MutableList<ImageSliderItem>, title: String,
                             category: ProductSubcategory, price: String, description: String) {
        val photoFiles = getPhotoFilesWithCorrectOrder(photos)

        val request = UpdateShopProductRequest(currentProduct.id, title, description, price.toFloat(),
                category.id, getLocationLatLng().latitude, getLocationLatLng().longitude,
                getLocationName(), photoFiles)

        addDisposable(
                updateShopProductUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSaveProductSuccess, this::handleApiError)
        )
    }

    private fun getLocationLatLng(): LatLng {
        if(selectedLocation == null) {
            return LatLng(currentProduct.product.lat, currentProduct.product.lon)
        }

        return selectedLocation!!.latLng
    }

    private fun getLocationName(): String {
        if(selectedLocation == null) {
            return currentProduct.product.pointName!!
        }

        return selectedLocation!!.name.toString()
    }

    private fun getPhotoFilesWithCorrectOrder(photos: MutableList<ImageSliderItem>): List<File> {
        val list = ArrayList<File>()

        if(currentHeader?.file != null) {
            list.add(currentHeader!!.file!!)
        }

        photos.remove(currentHeader!!)

        photos.forEach {
            if(!it.isAddPhoto && it.file != null) {
                list.add(it.file)
            }
        }

        return list
    }

    private fun handleSaveProductSuccess(response: EmptyResponse) {
        shopFiltersManager.clear()

        val product = currentProduct

        val title = resourceRepository.getString(R.string.congratulations_your_product_has_been_uploaded)
        val confirmButtonText = resourceRepository.getString(R.string.back_to_search)
        val cancelButtonText = resourceRepository.getString(R.string.to_my_product_list)

        view?.showConfirmationDialog(product.thumbnailPath, "", title, null,
                confirmButtonText, this::onBackToSearchClick, cancelButtonText, this::onBackToMyProductsClick)
    }
}