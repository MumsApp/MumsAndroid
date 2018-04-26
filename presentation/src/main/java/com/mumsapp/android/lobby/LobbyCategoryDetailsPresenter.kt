package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.lobby.LobbyPost
import javax.inject.Inject

class LobbyCategoryDetailsPresenter : LifecyclePresenter<LobbyCategoryDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    private var categoryId: Int? = null

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(categoryId: Int?) {
        this.categoryId = categoryId
    }

    override fun start() {
        showMockedData()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onCreatePostClick() {
        fragmentsNavigationService.openCreatePostFragment(true)
    }

    private fun showMockedData() {
        val posts = ArrayList<LobbyPost>()
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")
        posts += LobbyPost(0, "John Conor", "8m", "Post title", "Post content")

        view?.showPosts(posts, this::onReplyClick)
    }

    private fun onReplyClick(post: LobbyPost) {

    }
}