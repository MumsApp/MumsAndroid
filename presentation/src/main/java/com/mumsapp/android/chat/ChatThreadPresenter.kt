package com.mumsapp.android.chat

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class ChatThreadPresenter : LifecyclePresenter<ChatThreadView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    lateinit var chatThread: TemplateChatThread

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(chatThread: TemplateChatThread) {
        this.chatThread = chatThread
    }

    override fun start() {
        configureView()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    private fun configureView() {
        view?.setTitle(chatThread.recipient.userName)
        view?.showMessages("0", chatThread.messages)
    }
}