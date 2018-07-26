package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.lobby.CreateLobbyRoomRequest
import com.mumsapp.domain.model.lobby.LobbyRoomResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class CreateLobbyRoomUseCase(val repository: AppRepository,
                             @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                             schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<CreateLobbyRoomRequest, LobbyRoomResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: CreateLobbyRoomRequest): Observable<LobbyRoomResponse> {
        return repository.createLobbyRoom(param)
    }
}