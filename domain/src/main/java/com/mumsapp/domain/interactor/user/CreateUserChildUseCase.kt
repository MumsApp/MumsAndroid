package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.user.ChildRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class CreateUserChildUseCase(val repository: UserRepository,
                             @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                             schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<ChildRequest, UserResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: ChildRequest): Observable<UserResponse> {
        return repository.createChild(param.userId!!, param)
    }
}