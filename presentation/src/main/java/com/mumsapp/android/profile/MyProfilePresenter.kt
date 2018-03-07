package com.mumsapp.android.profile

import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.interactor.user.UpdateUserLocationUseCase
import com.mumsapp.domain.model.user.UpdateLocationRequest
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyProfilePresenter: LifecyclePresenter<MyProfileView> {

    private val getUserProfileUseCase: GetUserProfileUseCase
    private val sessionManager: SessionManager
    private val resourceRepository: ResourceRepository
    private val imagesRepository: ImagesRepository
    private val updateUserLocationUseCase: UpdateUserLocationUseCase


    @Inject
    constructor(getUserProfileUseCase: GetUserProfileUseCase, sessionManager: SessionManager,
                resourceRepository: ResourceRepository,
                imagesRepository: ImagesRepository,
                updateUserLocationUseCase: UpdateUserLocationUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase
        this.sessionManager = sessionManager
        this.resourceRepository = resourceRepository
        this.imagesRepository = imagesRepository
        this.updateUserLocationUseCase = updateUserLocationUseCase
    }

    override fun start() {
        loadAndUpdateUserProfile()
    }

    fun onSettingsClick() {
        view?.showAccountSettingsDialog()
    }

    private fun loadAndUpdateUserProfile() {
        val currentUser: UserResponse? = sessionManager.loadLoggedUser()
        addDisposable(
                getUserProfileUseCase.execute(Params(currentUser?.data!!.id, Params.LEVEL_FULL))
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSuccess, this::handleError)
        )
    }

    private fun handleSuccess(user: UserResponse) {
        sessionManager.saveLoggedUser(user)
        showUserDetails(user.data)
    }

    private fun handleError(throwable: Throwable) {
        view?.showSnackbar(throwable.message!!)
    }

    private fun showUserDetails(user: UserResponse.User) {
        val name: String = resourceRepository.getString(R.string.first_and_last_name_pattern,
                user.firstName, user.lastName)

        view?.showProfileInfo(name, user.description)

        if(user.photo.src != null) {
            val url = imagesRepository.getApiImageUrl(user.photo.src!!)
            view?.loadAvatar(url)
        }
    }

    fun onChangeClick() {
        view?.showUserDetailsSettingsDialog()
    }

    fun onEditLocationClickListener() {
        view?.showEditLocationScreen()
    }

    fun onLocationSwitchChanged(value: Boolean) {
        if(value) {
            view?.showLocation()
        } else {
            view?.hideLocation()
        }
    }

    fun onLocationSelected(place: Place) {
        updateLocationOnServer(place)
    }

    fun onLocationError(status: Status) {
        view?.showToast(resourceRepository.getString(R.string.location_choose_error))
    }

    private fun updateLocationOnServer(place: Place) {
        val request = UpdateLocationRequest(place.name.toString(), place.id,
                place.latLng.latitude, place.latLng.longitude,
                place.address.toString())
        addDisposable(
                updateUserLocationUseCase
                        .execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUpdateLocationSuccess, this::handleUpdateLocationError)
        )
    }

    private fun handleUpdateLocationSuccess(response: UserResponse) {
        val location = response.data.location
        view?.showNewLocation(location.latitude!!, location.longitude!!, location.formattedAddress!!)
    }

    private fun handleUpdateLocationError(throwable: Throwable) {
        view?.showToast(throwable.localizedMessage)
    }
}