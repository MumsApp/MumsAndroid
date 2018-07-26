package com.mumsapp.android.lobby

import android.Manifest
import android.net.Uri
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.CAMERA_REQUEST_CODE
import com.mumsapp.android.util.GALLERY_REQUEST_CODE
import com.mumsapp.domain.interactor.lobby.CreateLobbyRoomUseCase
import com.mumsapp.domain.model.lobby.CreateLobbyRoomRequest
import com.mumsapp.domain.model.lobby.LobbyRoomResponse
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.ValidationHelper
import java.io.File
import javax.inject.Inject

class CreateLobbyCategoryPresenter : LifecyclePresenter<CreateLobbyCategoryView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val filesHelper: FilesHelper
    private val validationHelper: ValidationHelper
    private val createLobbyRoomUseCase: CreateLobbyRoomUseCase

    private var chosenFile: File? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository, filesHelper: FilesHelper,
                validationHelper: ValidationHelper,
                createLobbyRoomUseCase: CreateLobbyRoomUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.filesHelper = filesHelper
        this.validationHelper = validationHelper
        this.createLobbyRoomUseCase = createLobbyRoomUseCase
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick(publicSwitchValue: Boolean, title: String?, description: String?) {
        if(validate(title, description, chosenFile)) {
            saveCategory(title!!, description!!, publicSwitchValue, chosenFile!!)
        } else {
            val error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
            view?.showToast(error)
        }
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

    private fun validate(title: String?, description: String?, file: File?): Boolean {
        var valid = true

        valid = valid && validationHelper.checkIsNotEmpty(title)
        valid = valid && validationHelper.checkIsNotEmpty(description)
        valid = valid && file != null

        return valid
    }

    private fun saveCategory(title: String, description: String, public: Boolean, file: File) {
        val request = CreateLobbyRoomRequest(title, description, public, file)

        addDisposable(
                createLobbyRoomUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSaveCategorySuccess, this::handleApiError)
        )
    }

    private fun handleSaveCategorySuccess(response: LobbyRoomResponse) {
        fragmentsNavigationService.popFragment()
    }
}