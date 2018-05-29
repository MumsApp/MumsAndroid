package com.mumsapp.android.chat

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.chat.GetChatThreadsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.chat.ChatThreadResponse
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class FriendsChatPresenter : LifecyclePresenter<ChatListView> {

    private val getChatThreadsUseCase: GetChatThreadsUseCase
    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(getChatThreadsUseCase: GetChatThreadsUseCase, fragmentsNavigationService: FragmentsNavigationService) {
        this.getChatThreadsUseCase = getChatThreadsUseCase
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    override fun start() {
        loadThreads()
    }

    fun onFiltersButtonClick() {

    }

    fun onSettingsButtonClick() {
        view?.openChatSettingsDialog()
    }

    fun onSearch(value: String) {

    }

    fun onChatThreadClick(item: TemplateChatThread) {
        fragmentsNavigationService.openChatThreadFragment(item, true)
    }

    private fun loadThreads() {
        addDisposable(
                getChatThreadsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleChatThreadsSuccess, this::handleChatThreadError)
        )
    }

    private fun handleChatThreadsSuccess(response: ChatThreadResponse) {
        view?.showItems(response.threads)
    }

    private fun handleChatThreadError(throwable: Throwable) {

    }
}