package com.mumsapp.android.profile

import android.Manifest
import android.net.Uri
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.*
import com.mumsapp.domain.interactor.user.*
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import com.mumsapp.domain.model.user.*
import com.mumsapp.domain.model.user.UserResponse.Child
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.FilesHelper
import com.mumsapp.domain.utils.SessionManager
import java.io.File
import javax.inject.Inject

class MyProfilePresenter : LifecyclePresenter<MyProfileView> {

    private val getUserProfileUseCase: GetUserProfileUseCase
    private val sessionManager: SessionManager
    private val resourceRepository: ResourceRepository
    private val imagesRepository: ImagesRepository
    private val updateUserLocationUseCase: UpdateUserLocationUseCase
    private val updateUserDetailsUseCase: UpdateUserDetailsUseCase
    private val createUserChildUseCase: CreateUserChildUseCase
    private val updateUserChildUseCase: UpdateUserChildUseCase
    private val deleteUserChildUseCase: DeleteUserChildUseCase
    private val childrenMapper: ChildrenMapper
    private val filesHelper: FilesHelper
    private val fragmentsNavigationService: FragmentsNavigationService
    private val updateAvatarUseCase: UpdateAvatarUseCase

    private var tmpCameraFile: File? = null
    private lateinit var currentUser: UserResponse.User


    @Inject
    constructor(getUserProfileUseCase: GetUserProfileUseCase, sessionManager: SessionManager,
                resourceRepository: ResourceRepository,
                imagesRepository: ImagesRepository,
                updateUserLocationUseCase: UpdateUserLocationUseCase,
                updateUserDetailsUseCase: UpdateUserDetailsUseCase,
                createUserChildUseCase: CreateUserChildUseCase,
                updateUserChildUseCase: UpdateUserChildUseCase,
                deleteUserChildUseCase: DeleteUserChildUseCase,
                childrenMapper: ChildrenMapper,
                filesHelper: FilesHelper,
                fragmentsNavigationService: FragmentsNavigationService,
                updateAvatarUseCase: UpdateAvatarUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase
        this.sessionManager = sessionManager
        this.resourceRepository = resourceRepository
        this.imagesRepository = imagesRepository
        this.updateUserLocationUseCase = updateUserLocationUseCase
        this.updateUserDetailsUseCase = updateUserDetailsUseCase
        this.createUserChildUseCase = createUserChildUseCase
        this.updateUserChildUseCase = updateUserChildUseCase
        this.deleteUserChildUseCase = deleteUserChildUseCase
        this.childrenMapper = childrenMapper
        this.filesHelper = filesHelper
        this.fragmentsNavigationService = fragmentsNavigationService
        this.updateAvatarUseCase = updateAvatarUseCase
    }

    override fun start() {
        loadAndUpdateUserProfile()
        showMockedOffers()
        showMockedFriends()
    }

    fun onSettingsClick() {
        view?.showAccountSettingsDialog()
    }

    private fun loadAndUpdateUserProfile() {
        val currentUser: UserResponse? = sessionManager.loadLoggedUser()
        addDisposable(
                getUserProfileUseCase.execute(Params(currentUser?.data!!.id, Params.LEVEL_FULL))
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUserLoadSuccess, this::handleApiError)
        )
    }

    private fun handleUserLoadSuccess(user: UserResponse) {
        currentUser = user.data
        showUserDetails(user.data)
    }

    private fun showUserDetails(user: UserResponse.User) {
        val name: String = resourceRepository.getString(R.string.first_and_last_name_pattern,
                user.firstName, user.lastName)

        view?.showProfileInfo(name, user.description)

        if (user.photo.src != null) {
            val url = imagesRepository.getApiImageUrl(user.photo.src!!)
            view?.loadAvatar(url)
        }

        if(user.location.enabled != null && user.location.enabled!!) {
            view?.showNewLocation(user.location.latitude!!, user.location.longitude!!, user.location.name!!)
        }

        if(user.children.isEmpty()) {
           view?.hideChildren()
        } else {
            view?.showChildren(user.children.toList(), this::onEditChildClick, this::onDeleteChildClick)
        }
    }

    fun onAvatarClick() {
        view?.askForPermissions(onGrantedCallback = {
            view?.showSelectImageSourceDialog(this::onGalleryClick, this::onCameraClick)
        }, onDeniedCallback = {
            view?.showSnackbar(resourceRepository.getString(R.string.memory_permission_explanation))
        }, permissions = *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    fun onGalleryImageReceived(uri: Uri) {
        val file = filesHelper.getFileFromGalleryUri(uri.toString())
        updateAvatar(file)
    }

    fun onCameraImageReceived() {
        updateAvatar(tmpCameraFile!!)
    }

    fun onChangeClick() {
        val user = sessionManager.loadLoggedUser()!!.data
        view?.showUserDetailsSettingsDialog(user.firstName, user.lastName, user.description,
                this::handleUserDataUpdate)
    }

    private fun handleUserDataUpdate(firstName: String, lastName: String, description: String) {
        val request = UpdateUserDetailsRequest(firstName, lastName, description)

        addDisposable(
                updateUserDetailsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUpdateUserDetailsSuccess, this::handleApiError))
    }

    private fun handleUpdateUserDetailsSuccess(user: UserResponse) {
        showUserDetails(user.data)
    }

    fun onEditLocationClick() {
        view?.showEditLocationScreen()
    }

    fun onLocationSwitchChanged(value: Boolean) {
        val loggedUser = sessionManager.loadLoggedUser()

        if(loggedUser?.data?.location?.placeId != null) {

            val location = loggedUser.data.location

            updateLocationOnServer(location.name!!, location.placeId!!, location.latitude!!.toDouble(),
                    location.longitude!!.toDouble(), location.formattedAddress!!, value)
        }

        if (value) {
            view?.showLocation()
        } else {
            view?.hideLocation()
        }
    }

    fun onLocationSelected(place: Place) {
        updateLocationOnServer(place, true)
    }

    fun onLocationError(status: Status) {
        view?.showToast(resourceRepository.getString(R.string.location_choose_error))
    }

    fun onAddMaleClick() {
        view?.showAddChildDialog(SEX_MALE, null, this::onSaveChildClick)
    }

    fun onAddFemaleClick() {
        view?.showAddChildDialog(SEX_FEMALE, null, this::onSaveChildClick)
    }

    fun onAddToComeClick() {
        view?.showAddChildDialog(SEX_TO_COME, null, this::onSaveChildClick)
    }

    private fun onGalleryClick() {
        fragmentsNavigationService.openGalleryActivityForResults(GALLERY_REQUEST_CODE)
    }

    private fun onCameraClick() {
        tmpCameraFile = createTemporaryFile()
        val uri = Uri.parse(filesHelper.getExportedUri(tmpCameraFile!!))

        fragmentsNavigationService.openCameraActivityForResults(uri, CAMERA_REQUEST_CODE)
    }

    private fun updateAvatar(file: File) {
        val request = UpdateAvatarRequest(currentUser.id, file)
        addDisposable(
                updateAvatarUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUpdateAvatarSuccess, this::handleApiError)
        )
    }

    private fun handleUpdateAvatarSuccess(response: EmptyResponse) {
        loadAndUpdateUserProfile()
    }

    private fun onEditChildClick(child: Child) {
        view?.showAddChildDialog(child.sex!!, child, this::onSaveChildClick)
    }

    private fun createTemporaryFile(): File {
        return filesHelper.createTemporaryFile("avatar", ".jpg")
    }

    private fun onDeleteChildClick(child: Child) {
        val title = resourceRepository.getString(R.string.are_you_sure_you_want_to_remove)
        val readableChild = childrenMapper.getReadableName(child)
        val description = resourceRepository.getString(R.string.from_your_children_list_question_mark, readableChild)
        val buttonText = resourceRepository.getString(R.string.yes_coma_remove)

        view?.showConfirmationDialog(title, description, buttonText, {deleteChild(child)})
    }

    private fun onSaveChildClick(child: Child) {
        if(child.id == null) {
            createChild(child)
        } else {
            updateChild(child)
        }
    }

    private fun updateLocationOnServer(place: Place, enabled: Boolean) {
        updateLocationOnServer(place.name.toString(), place.id,
                place.latLng.latitude, place.latLng.longitude,
                place.address.toString(), enabled)
    }

    private fun updateLocationOnServer(name: String, placeId: String, latitude: Double,
                                       longitude: Double, address: String, enabled: Boolean) {
        val request = UpdateLocationRequest(name, placeId, latitude, longitude, address, enabled)

        addDisposable(
                updateUserLocationUseCase
                        .execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUpdateLocationSuccess, this::handleApiError)
        )
    }

    private fun handleUpdateLocationSuccess(response: UserResponse) {
        if(response.data.location.enabled != null && response.data.location.enabled!!) {
            val location = response.data.location
            view?.showNewLocation(location.latitude!!, location.longitude!!, location.formattedAddress!!)
        } else {
            view?.hideLocation()
        }
    }

    private fun updateChild(child: Child) {
        val request = ChildRequest(currentUser.id, child.id, child.age!!, child.ageUnit!!, child.sex!!)
        addDisposable(
                updateUserChildUseCase
                        .execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUpdateChildSuccess, this::handleApiError)
        )
    }

    private fun handleUpdateChildSuccess(child: Child) {
        val children: MutableCollection<Child> = currentUser.children
        children.forEach{
            if(it.id == child.id) {
                children.remove(it)
            }
        }

        children.add(child)
        view?.showChildren(children.toList(), this::onEditChildClick, this::onDeleteChildClick)
    }

    private fun createChild(child: Child) {
        val request = ChildRequest(currentUser.id, child.id, child.age!!, child.ageUnit!!, child.sex!!)
        addDisposable(
                createUserChildUseCase
                        .execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleCreateChildSuccess, this::handleApiError)
        )
    }

    private fun handleCreateChildSuccess(user: UserResponse) {
        sessionManager.saveLoggedUser(user)
        val children: MutableCollection<Child> = user.data.children
        view?.notifyChildAdded(children.toList(), children.size - 1)
    }

    private fun deleteChild(child: Child) {
        val request = ChildRequest(currentUser.id, child.id, child.age!!, child.ageUnit!!, child.sex!!)
        addDisposable(deleteUserChildUseCase
                .execute(request)
                .compose(applyOverlaysToObservable())
                .subscribe({handleChildDeleted(child)}, this::handleApiError)
        )
    }

    private fun handleChildDeleted(child: Child) {
        val children: MutableCollection<Child> = currentUser.children
        val position = getPositionInArray(children, child)
        children.remove(child)
        view?.notifyChildRemoved(children.toList(), position)
    }

    private fun getPositionInArray(children: MutableCollection<Child>, child: Child) : Int {
        children.forEachIndexed( { i: Int, currentChild: Child ->
            if(child == currentChild) {
                return i
            }
        })

        throw IllegalStateException("Wrong child")
    }

    private fun showMockedOffers() {
        val offers = ArrayList<TemplateMumsAppOffer>()
        offers += TemplateMumsAppOffer("Kid's activities", true)
        offers += TemplateMumsAppOffer("Strollers", true)
        offers += TemplateMumsAppOffer("Nannies", false)
        offers += TemplateMumsAppOffer("Clothes", false)


        view?.showOffers(offers)
    }

    private fun showMockedFriends() {
        val members = ArrayList<TemplateChatRecipient>()
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")

        view?.showFriends(members)
    }
}