package com.mumsapp.android.lobby

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.lobby.CreateLobbyTopicPostUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.CreateLobbyTopicPostRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class CreateLobbyTopicPostPresenter : LifecyclePresenter<CreateLobbyTopicPostView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val createLobbyPostTopicUseCase: CreateLobbyTopicPostUseCase
    private val resourceRepository: ResourceRepository
    private val validationHelper: ValidationHelper

    lateinit var lobbyRoom: LobbyRoom
    lateinit var lobbyRoomTopic: LobbyRoomTopic

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                createLobbyPostTopicUseCase: CreateLobbyTopicPostUseCase,
                resourceRepository: ResourceRepository,
                validationHelper: ValidationHelper) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.createLobbyPostTopicUseCase = createLobbyPostTopicUseCase
        this.resourceRepository = resourceRepository
        this.validationHelper = validationHelper
    }

    fun setArguments(lobbyRoom: LobbyRoom, lobbyRoomTopic: LobbyRoomTopic) {
        this.lobbyRoom = lobbyRoom
        this.lobbyRoomTopic = lobbyRoomTopic
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick(description: String?) {
        if(validate(description)) {
            savePost(description)
        } else {
            val error = resourceRepository.getString(R.string.you_need_to_fill_all_fields)
            view?.showToast(error)
        }
    }

    private fun validate(description: String?): Boolean {
        var valid = true

        valid = valid && validationHelper.checkIsNotEmpty(description)

        return valid
    }

    private fun savePost(description: String?) {
        val request = CreateLobbyTopicPostRequest(lobbyRoom.id, lobbyRoomTopic.id, description!!)

        addDisposable(
                createLobbyPostTopicUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSavePostSuccess, this::handleApiError)
        )
    }

    private fun handleSavePostSuccess(response: EmptyResponse) {
        fragmentsNavigationService.popFragment()
    }
}