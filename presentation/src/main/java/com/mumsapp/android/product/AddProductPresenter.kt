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
    private var chosenPhotos: List<File> = ArrayList()

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
        Log.e("AddProductPresenter", uri.toString())
        view?.showImageHeader(uri)
    }

    fun onCameraImageReceived() {
        Log.e("AddProductPresenter", "camera")
        view?.showImageHeader(Uri.fromFile(tmpCameraFile))
    }

    private fun createTemporaryFile(): File {
        val count = chosenPhotos?.size
        val name = "photo$count"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }

    private fun addPhotoToView()
}