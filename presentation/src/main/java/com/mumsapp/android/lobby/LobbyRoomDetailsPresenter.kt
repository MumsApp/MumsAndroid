package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.DEFAULT_PAGE_SIZE
import com.mumsapp.domain.interactor.lobby.GetLobbyRoomTopicsUseCase
import com.mumsapp.domain.model.lobby.GetLobbyRoomTopicsRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.model.lobby.LobbyRoomTopicsResponse
import javax.inject.Inject

class LobbyRoomDetailsPresenter : LifecyclePresenter<LobbyRoomDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getLobbyRoomTopicsUseCase: GetLobbyRoomTopicsUseCase

    lateinit var lobbyRoom: LobbyRoom

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, getLobbyRoomTopicsUseCase: GetLobbyRoomTopicsUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getLobbyRoomTopicsUseCase = getLobbyRoomTopicsUseCase
    }

    fun setArguments(lobbyRoom: LobbyRoom) {
        this.lobbyRoom = lobbyRoom
    }

    override fun start() {
        view?.setTitle(lobbyRoom.title)
        loadTopics(1, DEFAULT_PAGE_SIZE)
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {
        fragmentsNavigationService.openCreateLobbyTopicFragment(lobbyRoom!!.id, true)
    }

    private fun onReplyClick(post: LobbyRoomTopic) {

    }

    private fun onUserClick(post: LobbyRoomTopic) {
        fragmentsNavigationService.openUserProfileFragment(true)
    }

    private fun loadTopics(page: Int, perPage: Int) {
        val request = GetLobbyRoomTopicsRequest(lobbyRoom.id, page, perPage)
        addDisposable(
                getLobbyRoomTopicsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadTopicsSuccess, this::handleApiError)
        )
    }

    private fun handleLoadTopicsSuccess(response: LobbyRoomTopicsResponse) {
        view?.setupPagination(response.data.pages, this::handlePageChange)
        view?.showTopics(response.data.posts, this::onReplyClick, this::onUserClick)
    }

    private fun handlePageChange(page: Int) {
        loadTopics(page, DEFAULT_PAGE_SIZE)
    }
}