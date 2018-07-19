package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.lobby.LobbyFavouriteRequest
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class AddLobbyToFavouriteUseCase(val repository: AppRepository,
                                 @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                 schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<LobbyFavouriteRequest, LobbyRoom>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: LobbyFavouriteRequest): Observable<LobbyRoom> {
        return repository.addLobbyToFavourite(param.lobby.id)
                .map { param.lobby }
    }
}