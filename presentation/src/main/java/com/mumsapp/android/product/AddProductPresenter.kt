package com.mumsapp.android.product

import android.Manifest
import android.net.Uri
import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class AddProductPresenter : LifecyclePresenter<AddProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private val resourceRepository: ResourceRepository

    private var tmpCameraFile: File? = null
    private var chosenPhotos: MutableList<ImageSliderItem> = ArrayList()
    private var currentHeader: ImageSliderItem? = null
    private var selectedLocation: Place? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
        this.resourceRepository = resourceRepository
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onAddPhotoClick() {
        view?.askForPermissions(onGrantedCallback = {
            view?.showSelectImageSourceDialog()
        }, onDeniedCallback = {
            view?.showSnackbar(resourceRepository.getString(R.string.memory_permission_explanation))
        }, permissions = *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    fun onGalleryClick() {
        fragmentsNavigationService.openGalleryActivityForResults(GALLERY_REQUEST_CODE)
    }

    fun onCameraClick() {
        tmpCameraFile = createTemporaryFile()
        val uri = Uri.parse(filesHelper.getExportedUri(tmpCameraFile!!))

        fragmentsNavigationService.openCameraActivityForResults(uri, CAMERA_REQUEST_CODE)
    }

    fun onGalleryImageReceived(uri: Uri) {
        addPhotoToView(uri)
    }

    fun onCameraImageReceived() {
        addPhotoToView(Uri.fromFile(tmpCameraFile))
    }

    fun onPhotoSliderItemClick(item: ImageSliderItem) {
        if(item.isAddPhoto) {
            onAddPhotoClick()
        } else {
            currentHeader = item
            view?.showImageHeader(currentHeader!!.uri!!)
        }
    }

    fun onEditLocationClick() {
        view?.showEditLocationScreen()
    }

    fun onLocationSelected(place: Place) {
        this.selectedLocation = place

        view?.showNewLocation(place.latLng.latitude, place.latLng.longitude, place.address.toString())
    }

    fun onUploadButtonClick() {
        val title = resourceRepository.getString(R.string.congratulations_your_product_has_been_uploaded)
        val confirmButtonText = resourceRepository.getString(R.string.back_to_search)
        val cancelButtonText = resourceRepository.getString(R.string.to_my_product_list)

        view?.showConfirmationDialog(currentHeader?.uri, "", title, null,
                confirmButtonText, cancelButtonText)
    }

    fun onConfirmDialogButtonClick() {
        fragmentsNavigationService.popFragment()
        fragmentsNavigationService.popFragment()
    }

    fun onCancelDialogButtonClick() {
        fragmentsNavigationService.popFragment()
    }

    private fun createTemporaryFile(): File {
        val count = chosenPhotos?.size
        val name = "photo$count"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }

    private fun addPhotoToView(uri: Uri) {
        val item = ImageSliderItem(uri, false)
        if(currentHeader == null) {
            currentHeader = item
            view?.showImageHeader(currentHeader!!.uri!!)
        }

        chosenPhotos.add(item)

        if(chosenPhotos.size < 2) {
            if(!chosenPhotos[0].isAddPhoto) {
                chosenPhotos.add(0, ImageSliderItem(null, true))
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
}