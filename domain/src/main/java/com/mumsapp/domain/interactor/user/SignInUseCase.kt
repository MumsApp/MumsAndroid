package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class SignInUseCase(val repository: UserRepository, tokenService: TokenPersistenceService,
                    getUserProfileUseCase: GetUserProfileUseCase,
                    schedulerProvider: SchedulerProvider) :
        BaseSignInUseCase<SignInRequest>(getUserProfileUseCase, tokenService, schedulerProvider) {

    override fun createTokenObservable(param: SignInRequest): Observable<Token> {
        return repository
                .requestToken(param)
    }
}