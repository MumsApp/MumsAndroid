package com.mumsapp.android.product

import android.net.Uri
import android.util.Log
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class AddProductPresenter : LifecyclePresenter<AddProductView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private var tmpCameraFile: File? = null
    private var chosenPhotos: MutableList<ImageSliderItem> = ArrayList()
    private var currentHeader: ImageSliderItem? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onAddPhotoClick() {
        view?.showSelectImageSourceDialog()
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

    fun onPhotoSliderItemClick(item: ImageSliderItem) {
        if(item.isAddPhoto) {
            onAddPhotoClick()
        } else {
            currentHeader = item
            view?.showImageHeader(currentHeader!!.uri!!)
        }
    }
}