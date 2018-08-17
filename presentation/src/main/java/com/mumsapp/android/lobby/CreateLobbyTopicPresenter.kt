package com.mumsapp.android.lobby

import android.Manifest
import android.net.Uri
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.utils.CAMERA_REQUEST_CODE
import com.mumsapp.domain.utils.GALLERY_REQUEST_CODE
import com.mumsapp.domain.interactor.lobby.CreateLobbyRoomTopicUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.*
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.ValidationHelper
import java.io.File
import javax.inject.Inject

class CreateLobbyTopicPresenter : LifecyclePresenter<CreateLobbyTopicView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val filesHelper: FilesHelper
    private val resourceRepository: ResourceRepository
    private val validationHelper: ValidationHelper
    private val createLobbyRoomTopicUseCase: CreateLobbyRoomTopicUseCase

    lateinit var lobbyRoom: LobbyRoom

    private var chosenFile: File? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, filesHelper: FilesHelper,
                resourceRepository: ResourceRepository,
                validationHelper: ValidationHelper,
                createLobbyRoomTopicUseCase: CreateLobbyRoomTopicUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.filesHelper = filesHelper
        this.resourceRepository = resourceRepository
        this.validationHelper = validationHelper
        this.createLobbyRoomTopicUseCase = createLobbyRoomTopicUseCase
    }

    fun setArguments(lobbyRoom: LobbyRoom) {
        this.lobbyRoom = lobbyRoom
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick(title: String?, description: String?) {
        if(validate(title, description)) {
            saveTopic(title!!, description!!, chosenFile)
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

    private fun onGalleryClick() {
        fragmentsNavigationService.openGalleryActivityForResults(GALLERY_REQUEST_CODE)
    }

    private fun onCameraClick() {
        chosenFile = createTemporaryFile()
        val uri = Uri.parse(filesHelper.getExportedUri(chosenFile!!))

        fragmentsNavigationService.openCameraActivityForResults(uri, CAMERA_REQUEST_CODE)
    }

    fun onGalleryImageReceived(uri: Uri) {
        chosenFile = filesHelper.getFileFromGalleryUri(uri.toString())
        view?.showImage(uri)
    }

    fun onCameraImageReceived() {
        view?.showImage(Uri.fromFile(chosenFile))
    }

    private fun createTemporaryFile(): File {
        val name = "photo"

        return filesHelper.createTemporaryFile(name, ".jpg")
    }

    private fun validate(title: String?, description: String?): Boolean {
        var valid = true

        valid = valid && validationHelper.checkIsNotEmpty(title)
        valid = valid && validationHelper.checkIsNotEmpty(description)

        return valid
    }

    private fun saveTopic(title: String, description: String, file: File?) {
        val request = CreateLobbyRoomTopicRequest(lobbyRoom.id, title, description, file)

        addDisposable(
                createLobbyRoomTopicUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSaveTopicSuccess, this::handleApiError)
        )
    }

    private fun handleSaveTopicSuccess(response: EmptyResponse) {
        fragmentsNavigationService.popFragment()
    }
}