package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.CreateLobbyRoomTopicRequest
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class CreateLobbyRoomTopicUseCase(val repository: AppRepository,
                                  @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                  schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<CreateLobbyRoomTopicRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: CreateLobbyRoomTopicRequest): Observable<EmptyResponse> {
        return repository.createLobbyRoomTopic(param)
    }
}