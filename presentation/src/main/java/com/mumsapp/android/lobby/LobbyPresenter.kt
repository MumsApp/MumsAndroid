package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.lobby.GetLobbyRoomsUseCase
import com.mumsapp.domain.interactor.lobby.SearchLobbyRoomsUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.lobby.SearchLobbyRequest
import javax.inject.Inject

class LobbyPresenter : LifecyclePresenter<LobbyView> {

    private val getLobbyItemsUseCase: GetLobbyRoomsUseCase
    private val fragmentsNavigationService: FragmentsNavigationService
    private val searchLobbyRoomsUseCase: SearchLobbyRoomsUseCase

    @Inject
    constructor(getLobbyItemsUseCace: GetLobbyRoomsUseCase,
                fragmentsNavigationService: FragmentsNavigationService,
                searchLobbyRoomsUseCase: SearchLobbyRoomsUseCase) {
        this.getLobbyItemsUseCase = getLobbyItemsUseCace
        this.fragmentsNavigationService = fragmentsNavigationService
        this.searchLobbyRoomsUseCase = searchLobbyRoomsUseCase
    }

    fun onFiltersButtonClick() {

    }

    fun onSearch(value: String) {
        searchItems(value)
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
                        .subscribe(this::handleLoadLobbySuccess, this::handleApiError)
        )
    }

    private fun searchItems(value: String) {
        val param = SearchLobbyRequest(value, true)
        addDisposable(
            searchLobbyRoomsUseCase.execute(param)
                    .compose(applyOverlaysToObservable())
                    .subscribe(this::handleLoadLobbySuccess, this::handleApiError)
        )
    }

    private fun handleLoadLobbySuccess(response: LobbyResponse) {
        view?.showItems(response.data, this::onLobbySwitchChanged)
    }

    private fun onLobbySwitchChanged(item: LobbyRoom, value: Boolean) {

    }
}