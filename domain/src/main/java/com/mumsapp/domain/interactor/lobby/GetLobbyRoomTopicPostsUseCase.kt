package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.lobby.GetLobbyRoomTopicPostsRequest
import com.mumsapp.domain.model.lobby.LobbyRoomTopicPostsResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetLobbyRoomTopicPostsUseCase(val repository: AppRepository,
                                    @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                    schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<GetLobbyRoomTopicPostsRequest, LobbyRoomTopicPostsResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: GetLobbyRoomTopicPostsRequest): Observable<LobbyRoomTopicPostsResponse> {
        return repository.getLobbyRoomTopicPosts(param.lobbyRoomId, param.lobbyTopicId, param.page, param.perPage)
    }
}