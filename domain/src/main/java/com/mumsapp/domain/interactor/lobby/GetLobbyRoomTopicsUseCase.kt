package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.lobby.GetLobbyRoomTopicsRequest
import com.mumsapp.domain.model.lobby.LobbyRoomTopicsResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetLobbyRoomTopicsUseCase(val repository: AppRepository,
                                @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<GetLobbyRoomTopicsRequest, LobbyRoomTopicsResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: GetLobbyRoomTopicsRequest): Observable<LobbyRoomTopicsResponse> {
        return repository.getLobbyRoomTopics(param.lobbyRoomId, param.page, param.perPage)
    }
}