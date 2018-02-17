package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.GoogleSignInRequest
import com.mumsapp.domain.repository.GoogleRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class GoogleSignInUseCase(private val googleRepository: GoogleRepository, val userRepository: UserRepository,
                          tokenService: TokenPersistenceService,
                          getUserProfileUseCase: GetUserProfileUseCase,
                          schedulerProvider: SchedulerProvider) :
        BaseSignInUseCase<EmptyRequest>(getUserProfileUseCase, tokenService, schedulerProvider)  {


    override fun createTokenObservable(param: EmptyRequest): Observable<Token> {
        return googleRepository.getLoggedUser()
                .observeOn(schedulerProvider.provideIOScheduler())
                .flatMap {
                    val request = GoogleSignInRequest(it.email, it.authToken)
                    userRepository.signInGoogle(request)
                }
    }
}