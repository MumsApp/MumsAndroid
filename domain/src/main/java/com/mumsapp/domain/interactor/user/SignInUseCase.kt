package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class SignInUseCase(val repository: UserRepository, val tokenService: TokenPersistenceService,
                    val getUserProfileUseCase: GetUserProfileUseCase,
                    schedulerProvider: SchedulerProvider) :
        BaseUseCase<SignInRequest, UserResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: SignInRequest): Observable<UserResponse> {
        return repository
                .requestToken(param)
                .flatMap(this::saveTokenAndGetUserProfile)
    }

    private fun saveTokenAndGetUserProfile(token: Token): Observable<UserResponse> {
        tokenService.saveToken(token)
        return getUserProfileUseCase.execute(EmptyRequest())
    }
}