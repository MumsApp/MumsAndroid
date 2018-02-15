package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.FacebookSignInRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.FacebookRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class FacebookSignInUseCase(val facebookRepository: FacebookRepository, val userRepository: UserRepository,
                            val tokenService: TokenPersistenceService,
                            val getUserProfileUseCase: GetUserProfileUseCase,
                            schedulerProvider: SchedulerProvider)
    : BaseUseCase<EmptyRequest, UserResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: EmptyRequest): Observable<UserResponse> {
        return facebookRepository.getFacebookUser()
                .flatMap {
                    val request = FacebookSignInRequest(it.email, it.authToken)
                    userRepository.signInFacebook(request)
                }
                .flatMap { saveTokenAndGetUserProfile(it) }
    }

    private fun saveTokenAndGetUserProfile(token: Token): Observable<UserResponse> {
        tokenService.saveToken(token)
        return getUserProfileUseCase.execute(EmptyRequest())
    }
}