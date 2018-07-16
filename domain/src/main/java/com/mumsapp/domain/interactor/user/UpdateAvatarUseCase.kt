package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.UpdateAvatarRequest
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class UpdateAvatarUseCase(val repository: UserRepository,
                          @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                          schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<UpdateAvatarRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: UpdateAvatarRequest): Observable<EmptyResponse> {
        return repository.updateAvatar(param.userId, param.file)
    }
}