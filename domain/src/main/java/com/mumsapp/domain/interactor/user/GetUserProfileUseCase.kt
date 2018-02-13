package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetUserProfileUseCase(val userRepository: UserRepository, schedulerProvider: SchedulerProvider)
    : BaseUseCase<EmptyRequest, UserResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: EmptyRequest): Observable<UserResponse> {
        val user = UserResponse(1, "test", "test", "test@test.com") //TODO: change this to real api

        return Observable.just(user)
    }
}