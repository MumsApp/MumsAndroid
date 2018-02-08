package com.mumsapp.domain.interactor.user.signUp

import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class SignUpUseCase(val repository: UserRepository, val schedulerProvider: SchedulerProvider) :
        BaseUseCase<SignUpRequest, EmptyResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: SignUpRequest): Observable<EmptyResponse> {
        return repository.createUser(param)
    }
}