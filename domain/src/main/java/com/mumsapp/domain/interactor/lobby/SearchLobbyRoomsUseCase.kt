package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.lobby.SearchLobbyRequest
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class SearchLobbyRoomsUseCase(val repository: AppRepository,
                              @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                              schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<SearchLobbyRequest, LobbyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: SearchLobbyRequest): Observable<LobbyResponse> {
        return repository.searchLobbyRooms(param, 1, Int.MAX_VALUE)
    }
}