package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class RemoveUserFriendUseCase(val repository: UserRepository,
                              @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                              schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<UserFriendRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: UserFriendRequest): Observable<EmptyResponse> {
        return repository.removeUserFriend(param.friendId)
    }
}