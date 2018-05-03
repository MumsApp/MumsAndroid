package com.mumsapp.android.lobby

import android.net.Uri
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class CreatePostPresenter : LifecyclePresenter<CreatePostView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper

    private var lobbyCategoryId = 0

    private var tmpCameraFile: File? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
    }

    fun setArguments(lobbyCategoryId: Int) {
        this.lobbyCategoryId = lobbyCategoryId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick() {
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
        view?.showImage(uri)
    }

    fun onCameraImageReceived() {
        view?.showImage(Uri.fromFile(tmpCameraFile))
    }

    private fun createTemporaryFile(): File {
        val name = "photo"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }
}