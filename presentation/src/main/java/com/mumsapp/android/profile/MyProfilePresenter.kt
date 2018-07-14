package com.mumsapp.android.profile

import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.util.SEX_FEMALE
import com.mumsapp.android.util.SEX_MALE
import com.mumsapp.android.util.SEX_TO_COME
import com.mumsapp.domain.interactor.user.*
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import com.mumsapp.domain.model.user.ChildRequest
import com.mumsapp.domain.model.user.UpdateLocationRequest
import com.mumsapp.domain.model.user.UpdateUserDetailsRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.model.user.UserResponse.Child
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.SessionManager
import java.util.function.Consumer
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

    lateinit var currentUser: UserResponse.User


    @Inject
    constructor(getUserProfileUseCase: GetUserProfileUseCase, sessionManager: SessionManager,
                resourceRepository: ResourceRepository,
                imagesRepository: ImagesRepository,
                updateUserLocationUseCase: UpdateUserLocationUseCase,
                updateUserDetailsUseCase: UpdateUserDetailsUseCase,
                createUserChildUseCase: CreateUserChildUseCase,
                updateUserChildUseCase: UpdateUserChildUseCase,
                deleteUserChildUseCase: DeleteUserChildUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase
        this.sessionManager = sessionManager
        this.resourceRepository = resourceRepository
        this.imagesRepository = imagesRepository
        this.updateUserLocationUseCase = updateUserLocationUseCase
        this.updateUserDetailsUseCase = updateUserDetailsUseCase
        this.createUserChildUseCase = createUserChildUseCase
        this.updateUserChildUseCase = updateUserChildUseCase
        this.deleteUserChildUseCase = deleteUserChildUseCase
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
            view?.showChildren(user.children.toList(), this::onEditChildClick)
        }
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

    private fun onEditChildClick(child: Child) {
        view?.showAddChildDialog(child.sex!!, child, this::onSaveChildClick)
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
        view?.showChildren(children.toList(), this::onEditChildClick)
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

    private fun handleCreateChildSuccess(child: Child) {
        val children: MutableCollection<Child> = currentUser.children
        children.add(child)
        view?.showChildren(children.toList(), this::onEditChildClick)
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