package com.mumsapp.android.profile

import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class MyProfilePresenter: BasePresenter<MyProfileView> {

    private val getUserProfileUseCase: GetUserProfileUseCase
    private val sessionManager: SessionManager
    private val resourceRepository: ResourceRepository
    private val imagesRepository: ImagesRepository


    @Inject
    constructor(getUserProfileUseCase: GetUserProfileUseCase, sessionManager: SessionManager,
                resourceRepository: ResourceRepository,
                imagesRepository: ImagesRepository) {
        this.getUserProfileUseCase = getUserProfileUseCase
        this.sessionManager = sessionManager
        this.resourceRepository = resourceRepository
        this.imagesRepository = imagesRepository
    }

    override fun start() {
        loadAndUpdateUserProfile()
    }

    fun onSettingsClick() {

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

        if(user.photo != null) {
            val url = imagesRepository.getApiImageUrl(user.photo!!.src)
            view?.loadAvatar(url)
        }
    }
}