package com.mumsapp.android.lobby

import android.Manifest
import android.net.Uri
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class CreateLobbyCategoryPresenter : LifecyclePresenter<CreateLobbyCategoryView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val filesHelper: FilesHelper

    private var chosenFile: File? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, resourceRepository: ResourceRepository, filesHelper: FilesHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.filesHelper = filesHelper
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick(publicSwitchValue: Boolean, title: String?, description: String?) {
        fragmentsNavigationService.popFragment()
    }

    fun onAddPhotoClick() {
        view?.askForPermissions(onGrantedCallback = {
            view?.showSelectImageSourceDialog(this::onGalleryClick, this::onCameraClick)
        }, onDeniedCallback = {
            view?.showSnackbar(resourceRepository.getString(R.string.memory_permission_explanation))
        }, permissions = *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    fun onGalleryImageReceived(uri: Uri) {
        chosenFile = filesHelper.getFileFromGalleryUri(uri.toString())
        addPhotoToView(uri)
    }

    fun onCameraImageReceived() {
        addPhotoToView(Uri.fromFile(chosenFile))
    }

    fun onAddMembersClick() {

    }

    private fun onGalleryClick() {
        fragmentsNavigationService.openGalleryActivityForResults(GALLERY_REQUEST_CODE)
    }

    private fun onCameraClick() {
        chosenFile = filesHelper.createTemporaryFile("photo", ".jpg")
        val uri = Uri.parse(filesHelper.getExportedUri(chosenFile!!))

        fragmentsNavigationService.openCameraActivityForResults(uri, CAMERA_REQUEST_CODE)
    }

    private fun addPhotoToView(uri: Uri) {
        view?.showCoverPhoto(uri)
    }
}