package com.mumsapp.android.mums_app_offers

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.chat.GetChatThreadsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.chat.ChatThreadResponse
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class MumsAppOffersPresenter : LifecyclePresenter<MumsAppOffersView> {

    private val getChatThreadsUseCase: GetChatThreadsUseCase
    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(getChatThreadsUseCase: GetChatThreadsUseCase,
                fragmentsNavigationService: FragmentsNavigationService) {
        this.getChatThreadsUseCase = getChatThreadsUseCase
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    override fun start() {
        loadThreads()
    }

    fun onChatThreadClick(item: TemplateChatThread) {
        fragmentsNavigationService.openMumsAppOfferDetailsFragment(true)
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