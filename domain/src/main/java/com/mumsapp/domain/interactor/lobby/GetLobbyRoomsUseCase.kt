package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.user.ChildRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetLobbyRoomsUseCase(val repository: AppRepository,
                           @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                           schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<EmptyRequest, LobbyResponse>(transformerProvider, schedulerProvider) {


    override fun createUseCaseObservable(param: EmptyRequest): Observable<LobbyResponse> {
        return repository.getLobbyRooms(1, Int.MAX_VALUE)
    }
}