package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import javax.inject.Inject

class LobbyRoomDetailsPresenter : LifecyclePresenter<LobbyRoomDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    private var categoryId: Int = 0

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(categoryId: Int) {
        this.categoryId = categoryId
    }

    override fun start() {
        showMockedData()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {
        fragmentsNavigationService.openCreatePostFragment(categoryId!!, true)
    }

    private fun showMockedData() {

    }

    private fun onReplyClick(post: LobbyRoomTopic) {

    }

    private fun onUserClick(post: LobbyRoomTopic) {
        fragmentsNavigationService.openUserProfileFragment(true)
    }
}