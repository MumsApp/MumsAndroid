package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.user.UpdateUserDetailsRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
import io.reactivex.Observable

class UpdateUserDetailsUseCase(val repository: UserRepository, val sessionManager: SessionManager,
                               @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                               schedulerProvider: SchedulerProvider) :
        AuthorizedUseCase<UpdateUserDetailsRequest, UserResponse>(transformerProvider, schedulerProvider){


    override fun createUseCaseObservable(param: UpdateUserDetailsRequest): Observable<UserResponse> {
        return repository.updateUserDetails(sessionManager.loadLoggedUser()!!.data.id, param)
                .map {
                    val user = sessionManager.loadLoggedUser()
                    val data = user!!.data
                    data.firstName = param.firstName
                    data.lastName = param.lastName
                    data.description = param.description
                    sessionManager.saveLoggedUser(user)

                    user
                }
    }
}