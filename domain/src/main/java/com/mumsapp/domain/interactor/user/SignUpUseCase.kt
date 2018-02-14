package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Singleton

@Singleton
class SignUpUseCase(val repository: UserRepository, val signInUseCase: SignInUseCase, schedulerProvider: SchedulerProvider) :
        BaseUseCase<SignUpRequest, UserResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: SignUpRequest): Observable<UserResponse> {
        return repository.createUser(param)
                .flatMap {
                    val request = SignInRequest(param.email, param.password)
                    signInUseCase.execute(request)
                }
    }
}