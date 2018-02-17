package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.FacebookSignInRequest
import com.mumsapp.domain.repository.FacebookRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class FacebookSignInUseCase(val facebookRepository: FacebookRepository, val userRepository: UserRepository,
                            tokenService: TokenPersistenceService,
                            getUserProfileUseCase: GetUserProfileUseCase,
                            schedulerProvider: SchedulerProvider)
    : BaseSignInUseCase<EmptyRequest>(getUserProfileUseCase, tokenService, schedulerProvider) {


    override fun createTokenObservable(param: EmptyRequest): Observable<Token> {
        return facebookRepository.getFacebookUser()
                .observeOn(schedulerProvider.provideIOScheduler())
                .flatMap {
                    val request = FacebookSignInRequest(it.email, it.authToken)
                    userRepository.signInFacebook(request)
                }
    }
}