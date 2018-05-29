package com.mumsapp.android.chat

import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class ChatPresenter : LifecyclePresenter<ChatView> {

    private val resourcesRepository: ResourceRepository
    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(resourcesRepository: ResourceRepository,
                fragmentsNavigationService: FragmentsNavigationService) {
        this.resourcesRepository = resourcesRepository
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    override fun start() {
        val friends = fragmentsNavigationService.createFriendsChatFragment()
        val others = fragmentsNavigationService.createFriendsChatFragment()
        val fragments = ArrayList<BaseFragment>()
        fragments.add(friends)
        fragments.add(others)

        val titles = ArrayList<String>()
        titles.add("Friends")
        titles.add(("Others"))

        view?.setItems(fragments, titles)
    }
}