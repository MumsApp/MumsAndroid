package com.mumsapp.android.product

import android.Manifest
import android.net.Uri
import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.shop.ProductSubcategory
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.*
import java.io.File

abstract class BaseProductFormPresenter<View : BaseProductFormView> : LifecyclePresenter<View> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private val resourceRepository: ResourceRepository
    private val shopFiltersManager: ShopFiltersManager
    private val validationHelper: ValidationHelper

    private var tmpCameraFile: File? = null
    private var chosenPhotos: MutableList<ImageSliderItem> = ArrayList()
    protected var currentHeader: ImageSliderItem? = null
    private var selectedLocation: Place? = null

    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository, shopFiltersManager: ShopFiltersManager,
                validationHelper: ValidationHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
        this.resourceRepository = resourceRepository
        this.shopFiltersManager = shopFiltersManager
        this.validationHelper = validationHelper
    }

    override fun create() {
        shopFiltersManager.clear()
    }

    override fun start() {
        restoreSelectedImages()
        showCorrectCategory()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onAddPhotoClick() {
        if(chosenPhotos.size > MAXIMUM_PRODUCT_PHOTOS) {
            view?.showSnackbar(resourceRepository.getString(R.string.you_can_add_maximum_of_photos,
                    MAXIMUM_PRODUCT_PHOTOS))
            return
        }

        view?.askForPermissions(onGrantedCallback = {
            view?.showSelectImageSourceDialog(this::onGalleryClick, this::onCameraClick)
        }, onDeniedCallback = {
            view?.showSnackbar(resourceRepository.getString(R.string.memory_permission_explanation))
        }, permissions = *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    fun onGalleryImageReceived(uri: Uri) {
        val file = filesHelper.getFileFromGalleryUri(uri.toString())
        addPhotoToView(uri, file)
    }

    fun onCameraImageReceived() {
        val file = File(tmpCameraFile?.toURI())
        addPhotoToView(Uri.fromFile(tmpCameraFile), file)
    }

    fun onPhotoSliderItemClick(item: ImageSliderItem) {
        if(item.isAddPhoto) {
            onAddPhotoClick()
        } else {
            currentHeader = item
            view?.showImageHeader(currentHeader!!.uri!!)
        }
    }

    fun onAddCategoryClick() {
        shopFiltersManager.clear()
        fragmentsNavigationService.openSelectCategoryFragment(true)
    }

    fun onAskForNewCategoryClick() {

    }

    fun onEditLocationClick() {
        view?.showEditLocationScreen()
    }

    fun onLocationSelected(place: Place) {
        this.selectedLocation = place

        view?.showNewLocation(place.latLng.latitude, place.latLng.longitude, place.address.toString())
    }

    fun onUploadButtonClick(title: String?, price: String?, description: String?) {
        if(validateAndShowErrors(chosenPhotos, title, shopFiltersManager.getSubcategory(), price,
                        description, selectedLocation)) {
            saveProduct(chosenPhotos, title!!, shopFiltersManager.getSubcategory()!!, price!!,
                    description!!, selectedLocation!!)
        }
    }

    private fun restoreSelectedImages() {
        if(currentHeader != null) {
            view?.showImageHeader(currentHeader!!.uri!!)
        }

        if(chosenPhotos.isNotEmpty()) {
            view?.showImageSlider(chosenPhotos, this::onPhotoRemoved)
        }
    }

    private fun showCorrectCategory() {
        val category = if(shopFiltersManager.getSubcategory() == null) {
            resourceRepository.getString(R.string.add_category)
        } else {
            shopFiltersManager.getSubcategory()!!.name
        }

        view?.showProductCategory(category)
    }

    private fun onGalleryClick() {
        fragmentsNavigationService.openGalleryActivityForResults(GALLERY_REQUEST_CODE)
    }

    private fun onCameraClick() {
        tmpCameraFile = createTemporaryFile()
        val uri = Uri.parse(filesHelper.getExportedUri(tmpCameraFile!!))

        fragmentsNavigationService.openCameraActivityForResults(uri, CAMERA_REQUEST_CODE)
    }

    private fun createTemporaryFile(): File {
        val count = chosenPhotos?.size
        val name = "photo$count"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }

    private fun addPhotoToView(uri: Uri, file: File) {
        val item = ImageSliderItem(uri, file, false)
        if(currentHeader == null) {
            currentHeader = item
            view?.showImageHeader(currentHeader!!.uri!!)
        }

        chosenPhotos.add(item)

        if(chosenPhotos.size < 2) {
            if(!chosenPhotos[0].isAddPhoto) {
                chosenPhotos.add(0, ImageSliderItem(null, null,true))
            }

            view?.showImageSlider(chosenPhotos, this::onPhotoRemoved)
        } else {
            view?.addImageSliderItem(chosenPhotos, chosenPhotos.lastIndex)
        }
    }

    private fun onPhotoRemoved(position: Int) {
        chosenPhotos.removeAt(position)
        view?.removeImageSliderItem(chosenPhotos, position)
    }

    private fun validateAndShowErrors(photos: MutableList<ImageSliderItem>?, title: String?,
                                      category: ProductSubcategory?, price: String?,
                                      description: String?, location: Place?): Boolean {
        var isValid = true
        var error: String? = null

        if(!validationHelper.checkIsNotEmpty(photos)) {
            isValid = false
            error = resourceRepository.getString(R.string.you_need_to_select_photo)
        }

        if(!validationHelper.checkIsNotEmpty(title)) {
            isValid = false
            error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
        }

        if(!validationHelper.checkIsNotEmpty(category)) {
            isValid = false
            error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
        }

        if(!validationHelper.checkIsCorrectPrice(price)) {
            isValid = false
            error = resourceRepository.getString(R.string.please_put_correct_price)
        }

        if(!validationHelper.checkIsNotEmpty(description)) {
            isValid = false
            error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
        }

        if(!validationHelper.checkIsNotEmpty(location)) {
            isValid = false
            error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
        }

        if(error != null) {
            view?.showToast(error)
        }

        return isValid
    }

    abstract fun saveProduct(photos: MutableList<ImageSliderItem>, title: String,
                             category: ProductSubcategory, price: String,
                             description: String, location: Place)

    protected fun onBackToSearchClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openShopFragment(true)
    }

    protected fun onBackToMyProductsClick() {
        fragmentsNavigationService.popFragmentsToRoot()
        fragmentsNavigationService.openShopFragment(true)
        fragmentsNavigationService.openMyProductsFragment(true)
    }
}