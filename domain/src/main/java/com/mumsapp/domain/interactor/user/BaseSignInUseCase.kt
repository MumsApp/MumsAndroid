package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.TokenPersistenceService
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import io.reactivex.Observable

abstract class BaseSignInUseCase<Request : BaseRequest>(val getUserProfileUseCase: GetUserProfileUseCase,
                                                        private val tokenService: TokenPersistenceService,
                                                        val schedulerProvider: SchedulerProvider) :
        BaseUseCase<Request, UserResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: Request): Observable<UserResponse> {
        return createTokenObservable(param)
                .flatMap { saveTokenAndGetUserProfile(it) }
    }

    abstract fun createTokenObservable(param: Request): Observable<Token>

    private fun saveTokenAndGetUserProfile(token: Token): Observable<UserResponse> {
        tokenService.saveToken(token)
        return getUserProfileUseCase.execute(Params(token.id,
                Params.LEVEL_FULL))
    }
}