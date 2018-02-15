package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.error.FacebookLoginError
import com.mumsapp.domain.model.error.ServerErrorException
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.FacebookSignInRequest
import com.mumsapp.domain.model.user.FacebookSignUpRequest
import com.mumsapp.domain.model.user.FacebookUserResponse
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.FacebookRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class FacebookSignUpUseCase(val facebookRepository: FacebookRepository, val userRepository: UserRepository,
                            val tokenService: TokenPersistenceService,
                            val getUserProfileUseCase: GetUserProfileUseCase,
                            val schedulerProvider: SchedulerProvider)
    : BaseUseCase<EmptyRequest, UserResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: EmptyRequest): Observable<UserResponse> {

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
                .flatMap { saveTokenAndGetUserProfile(it) }
    }

    private fun saveTokenAndGetUserProfile(token: Token): Observable<UserResponse> {
        tokenService.saveToken(token)
        return getUserProfileUseCase.execute(EmptyRequest())
    }
}