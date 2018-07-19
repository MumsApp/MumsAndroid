package com.mumsapp.android.lobby

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.lobby.*
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyFavouriteRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.lobby.SearchLobbyRequest
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class LobbyPresenter : LifecyclePresenter<LobbyView> {

    private val getLobbyItemsUseCase: GetLobbyRoomsUseCase
    private val fragmentsNavigationService: FragmentsNavigationService
    private val searchLobbyRoomsUseCase: SearchLobbyRoomsUseCase
    private val addLobbyToFavouriteUseCase: AddLobbyToFavouriteUseCase
    private val removeLobbyFromFavouriteUseCase: RemoveLobbyFromFavouriteUseCase
    private val leaveLobbyRoomUseCase: LeaveLobbyRoomUseCase
    private val resourceRepository: ResourceRepository

    @Inject
    constructor(getLobbyItemsUseCace: GetLobbyRoomsUseCase,
                fragmentsNavigationService: FragmentsNavigationService,
                searchLobbyRoomsUseCase: SearchLobbyRoomsUseCase,
                addLobbyToFavouriteUseCase: AddLobbyToFavouriteUseCase,
                removeLobbyFromFavouriteUseCase: RemoveLobbyFromFavouriteUseCase,
                leaveLobbyRoomUseCase: LeaveLobbyRoomUseCase,
                resourceRepository: ResourceRepository) {
        this.getLobbyItemsUseCase = getLobbyItemsUseCace
        this.fragmentsNavigationService = fragmentsNavigationService
        this.searchLobbyRoomsUseCase = searchLobbyRoomsUseCase
        this.addLobbyToFavouriteUseCase = addLobbyToFavouriteUseCase
        this.removeLobbyFromFavouriteUseCase = removeLobbyFromFavouriteUseCase
        this.leaveLobbyRoomUseCase = leaveLobbyRoomUseCase
        this.resourceRepository = resourceRepository
    }

    fun onFiltersButtonClick() {

    }

    fun onSearch(value: String) {
        searchItems(value)
    }

    fun onAddCategoryClick() {
        fragmentsNavigationService.openCreateLobbyCategoryFragment(true)
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
        view?.showItems(response.data, this::onLobbySwitchChanged, this::onLeaveLobbyRoomClick)
    }

    private fun onLobbySwitchChanged(item: LobbyRoom, value: Boolean) {
        item.isFavourite = value

        if(value) {
            addLobbyToFavourite(item)
        } else {
            removeLobbyFromFavourite(item)
        }
    }

    private fun addLobbyToFavourite(item: LobbyRoom) {
        val request = LobbyFavouriteRequest(item)
        addDisposable(
                addLobbyToFavouriteUseCase.execute(request)
                        .subscribe(this::handleLobbyFavouriteSuccess, this::handleApiError)
        )
    }

    private fun removeLobbyFromFavourite(item: LobbyRoom) {
        val request = LobbyFavouriteRequest(item)
        addDisposable(
                removeLobbyFromFavouriteUseCase.execute(request)
                        .subscribe(this::handleLobbyFavouriteSuccess, this::handleApiError)
        )
    }

    private fun handleLobbyFavouriteSuccess(item: LobbyRoom) {
        //ignored
    }

    private fun onLeaveLobbyRoomClick(item: LobbyRoom) {
        val title = resourceRepository.getString(R.string.are_you_sure_you_want_to_remove)
        val readableChild = item.title
        val description = resourceRepository.getString(R.string.from_your_lobby_rooms_list_question_mark, readableChild)
        val buttonText = resourceRepository.getString(R.string.yes_coma_remove)

        view?.showConfirmationDialog(title, description, buttonText, {leaveLobbyRoom(item)})
    }

    private fun leaveLobbyRoom(item: LobbyRoom) {
        val request = LobbyFavouriteRequest(item)
        addDisposable(
                leaveLobbyRoomUseCase.execute(request)
                        .subscribe(this::handleLeaveLobbyRoomSuccess, this::handleApiError)
        )
    }

    private fun handleLeaveLobbyRoomSuccess(item: LobbyRoom) {
        loadItems()
    }
}