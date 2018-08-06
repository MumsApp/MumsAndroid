package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.CreateLobbyTopicPostRequest
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class CreateLobbyTopicPostUseCase(val repository: AppRepository,
                                  @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                  schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<CreateLobbyTopicPostRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: CreateLobbyTopicPostRequest): Observable<EmptyResponse> {
        return repository.createLobbyTopicPost(param)
    }
}