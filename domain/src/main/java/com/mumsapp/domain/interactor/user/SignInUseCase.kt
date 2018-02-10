package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class SignInUseCase(val repository: UserRepository, schedulerProvider: SchedulerProvider) :
        BaseUseCase<SignInRequest, UserResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: SignInRequest): Observable<UserResponse> {
        return Observable.just(UserResponse(1, "fsdfsd", "fsdsdfds", "sfdfsdf"))
    }
}