package com.mumsapp.data.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.*
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl : BaseRestRepository, UserRepository {

    private val restApi: PublicRestApi

    @Inject
    constructor(restApi: PublicRestApi, exceptionDispatcher: ExceptionDispatcher,
                serializationHelper: SerializationHelper,
                resourceRepository: ResourceRepository) : super(exceptionDispatcher, serializationHelper, resourceRepository) {
        this.restApi = restApi
    }

    override fun createUser(request: SignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.createUser(request))
    }

    override fun requestToken(request: SignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.loginCheck(request))
    }

    override fun signUpFacebook(request: FacebookSignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.facebookCreate(request))
    }

    override fun signInFacebook(request: FacebookSignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.facebookLogin(request))
    }
}