package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import javax.inject.Inject

class CreateLobbyTopicPostPresenter : LifecyclePresenter<CreateLobbyTopicPostView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    lateinit var lobbyRoom: LobbyRoom
    lateinit var lobbyRoomTopic: LobbyRoomTopic

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(lobbyRoom: LobbyRoom, lobbyRoomTopic: LobbyRoomTopic) {
        this.lobbyRoom = lobbyRoom
        this.lobbyRoomTopic = lobbyRoomTopic
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onDoneClick(description: String?) {

    }
}