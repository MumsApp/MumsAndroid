package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.error.GoogleLoginError
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.GoogleSignInRequest
import com.mumsapp.domain.model.user.GoogleSignUpRequest
import com.mumsapp.domain.model.user.GoogleUserResponse
import com.mumsapp.domain.repository.GoogleRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class GoogleSignUpUseCase(private val googleRepository: GoogleRepository, val userRepository: UserRepository,
                          tokenService: TokenPersistenceService,
                          getUserProfileUseCase: GetUserProfileUseCase,
                          schedulerProvider: SchedulerProvider) :
        BaseSignInUseCase<EmptyRequest>(getUserProfileUseCase, tokenService, schedulerProvider) {


    override fun createTokenObservable(param: EmptyRequest): Observable<Token> {

        var googleResponse: GoogleUserResponse? = null

        return googleRepository.getLoggedUser()
                .observeOn(schedulerProvider.provideIOScheduler())
                .flatMap {
                    googleResponse = it
                    val request = GoogleSignUpRequest(it.email, it.authToken, it.firstName!!, it.lastName!!)
                    userRepository.signUpGoogle(request)
                }
                .flatMap {
                    if(googleResponse != null) {
                        val request = GoogleSignInRequest(googleResponse!!.email, googleResponse!!.authToken)
                        userRepository.signInGoogle(request)
                    } else {
                        throw GoogleLoginError("")
                    }
                }
    }
}