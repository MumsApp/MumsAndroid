package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.error.FacebookLoginError
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.FacebookSignInRequest
import com.mumsapp.domain.model.user.FacebookSignUpRequest
import com.mumsapp.domain.model.user.FacebookUserResponse
import com.mumsapp.domain.repository.FacebookRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class FacebookSignUpUseCase(private val facebookRepository: FacebookRepository, val userRepository: UserRepository,
                            private val tokenService: TokenPersistenceService,
                            getUserProfileUseCase: GetUserProfileUseCase,
                            schedulerProvider: SchedulerProvider)
    : BaseSignInUseCase<EmptyRequest>(getUserProfileUseCase, tokenService, schedulerProvider) {


    override fun createTokenObservable(param: EmptyRequest): Observable<Token> {

        var facebookResponse: FacebookUserResponse? = null

        return facebookRepository.getFacebookUser()
                .observeOn(schedulerProvider.provideIOScheduler())
                .flatMap {
                    facebookResponse = it
                    val request = FacebookSignUpRequest(it.email, it.authToken, it.firstName, it.lastName)
                    userRepository.signUpFacebook(request)
                }
                .flatMap {
                    if(facebookResponse != null) {
                        val request = FacebookSignInRequest(facebookResponse!!.email, facebookResponse!!.authToken)
                        userRepository.signInFacebook(request)
                    } else {
                        throw FacebookLoginError("")
                    }
                }
    }
}