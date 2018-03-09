package com.mumsapp.domain.interactor.transformers

import com.mumsapp.domain.model.error.RetryForcedException
import com.mumsapp.domain.model.error.UnauthorizedException
import com.mumsapp.domain.model.identity.RefreshTokenRequest
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function

class AuthorizationTransformerProvider : UseCaseTransformerProvider {

    private val userRepository: UserRepository
    private val tokenPersistenceService: TokenPersistenceService

    constructor(userRepository: UserRepository, tokenPersistenceService: TokenPersistenceService) {
        this.userRepository = userRepository
        this.tokenPersistenceService = tokenPersistenceService
    }

    override fun <T> get(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.onErrorResumeNext(Function {
                handleApiErrors(it)
            })
                    .retry(1)
        }
    }

    private fun <T> handleApiErrors(throwable: Throwable): Observable<T> {
        if (throwable is UnauthorizedException) {
            val expiredToken = tokenPersistenceService.getToken()

            if (expiredToken != null) {
                val request = RefreshTokenRequest(expiredToken.refreshToken)
                return userRepository
                        .refreshToken(request)
                        .map({ newToken ->
                            if (newToken != null) {
                                tokenPersistenceService.saveToken(newToken)
                            }

                            throw RetryForcedException()
                        })
            }
        }

        return Observable.error<T>(throwable)
    }
}