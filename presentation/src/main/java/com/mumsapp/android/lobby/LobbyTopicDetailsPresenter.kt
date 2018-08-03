package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import javax.inject.Inject

class LobbyTopicDetailsPresenter : LifecyclePresenter<LobbyTopicDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    lateinit var lobbyRoomTopic: LobbyRoomTopic

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(lobbyRoomTopic: LobbyRoomTopic) {
        this.lobbyRoomTopic = lobbyRoomTopic
    }

    override fun start() {
        view?.setTitle(lobbyRoomTopic.title)
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {

    }
}