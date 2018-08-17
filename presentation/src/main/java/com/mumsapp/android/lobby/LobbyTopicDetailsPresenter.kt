package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.utils.DEFAULT_PAGE_SIZE
import com.mumsapp.domain.interactor.lobby.GetLobbyRoomTopicPostsUseCase
import com.mumsapp.domain.model.lobby.*
import javax.inject.Inject

class LobbyTopicDetailsPresenter : LifecyclePresenter<LobbyTopicDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getLobbyRoomTopicPostsUseCase: GetLobbyRoomTopicPostsUseCase

    lateinit var lobbyRoom: LobbyRoom
    lateinit var lobbyRoomTopic: LobbyRoomTopic

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                getLobbyRoomTopicPostsUseCase: GetLobbyRoomTopicPostsUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getLobbyRoomTopicPostsUseCase = getLobbyRoomTopicPostsUseCase
    }

    fun setArguments(lobbyRoom: LobbyRoom, lobbyRoomTopic: LobbyRoomTopic) {
        this.lobbyRoom = lobbyRoom
        this.lobbyRoomTopic = lobbyRoomTopic
    }

    override fun start() {
        view?.setTitle(lobbyRoomTopic.title)
        loadPosts(1, DEFAULT_PAGE_SIZE)
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {
        fragmentsNavigationService.openCreateLobbyTopicPostFragment(lobbyRoom, lobbyRoomTopic, true)
    }

    private fun onUserClick(post: LobbyRoomTopicPost) {
        fragmentsNavigationService.openUserProfileFragment(post.author.id, true)
    }

    private fun loadPosts(page: Int, pageSize: Int) {
        val request = GetLobbyRoomTopicPostsRequest(lobbyRoom.id, lobbyRoomTopic.id, page, pageSize)
        addDisposable(
                getLobbyRoomTopicPostsUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadPostsSuccess, this::handleApiError)
        )
    }

    private fun handleLoadPostsSuccess(response: LobbyRoomTopicPostsResponse) {
        view?.setupPagination(response.data.pages, this::handlePageChange)
        view?.showPosts(response.data.posts, this::onUserClick)
    }

    private fun handlePageChange(page: Int) {
        loadPosts(page, DEFAULT_PAGE_SIZE)
    }
}