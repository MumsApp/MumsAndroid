package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.lobby.GetLobbyRoomsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyResponse
import javax.inject.Inject

class LobbyPresenter : LifecyclePresenter<LobbyView> {

    private val getLobbyItemsUseCase: GetLobbyRoomsUseCase
    private val fragmentsNavigationService: FragmentsNavigationService

    @Inject
    constructor(getLobbyItemsUseCace: GetLobbyRoomsUseCase,
                fragmentsNavigationService: FragmentsNavigationService) {
        this.getLobbyItemsUseCase = getLobbyItemsUseCace
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun onFiltersButtonClick() {

    }

    fun onSearch(value: String) {

    }

    fun onAddCategoryClick() {

    }

    fun onLobbyItemClick(item: LobbyRoom) {
        fragmentsNavigationService.openLobbyCategoryDetailsFragment(item.id, true)
    }

    override fun start() {
        loadItems()
    }

    private fun loadItems() {
        addDisposable(
                getLobbyItemsUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadLobbySuccess)
        )
    }

    private fun handleLoadLobbySuccess(response: LobbyResponse) {
        view?.showItems(response.data, this::onLobbySwitchChanged)
    }

    private fun onLobbySwitchChanged(item: LobbyRoom, value: Boolean) {

    }
}