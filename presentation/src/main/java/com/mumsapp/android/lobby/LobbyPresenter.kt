package com.mumsapp.android.lobby

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.domain.interactor.lobby.GetLobbyItemsUseCase
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyItem
import com.mumsapp.domain.model.lobby.LobbyResponse
import javax.inject.Inject

class LobbyPresenter : LifecyclePresenter<LobbyView> {

    private val getLobbyItemsUseCase: GetLobbyItemsUseCase

    @Inject
    constructor(getLobbyItemsUseCace: GetLobbyItemsUseCase) {
        this.getLobbyItemsUseCase = getLobbyItemsUseCace
    }

    fun onFiltersButtonClick() {

    }

    fun onSearch(value: String) {

    }

    fun onAddCategoryClick() {

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
        view?.showItems(response.items, this::onLobbySwitchChanged)
    }

    private fun onLobbySwitchChanged(item: LobbyItem, value: Boolean) {

    }
}