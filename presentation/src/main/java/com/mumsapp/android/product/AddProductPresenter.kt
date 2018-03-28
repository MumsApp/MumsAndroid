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
    private var chosenPhotos: List<File>? = null

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
        fragmentsNavigationService.openCameraActivityForResults(Uri.fromFile(tmpCameraFile), CAMERA_REQUEST_CODE)
    }

    fun onGalleryImageReceived(uri: Uri) {
        Log.e("AddProductPresenter", uri.toString())
    }

    fun onCameraImageReceived() {
        Log.e("AddProductPresenter", "camera")
    }

    private fun createTemporaryFile(): File {
        val count = chosenPhotos?.size
        val name = "photo$count"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }
}