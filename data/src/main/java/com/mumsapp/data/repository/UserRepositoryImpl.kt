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

    override fun signUpGoogle(request: GoogleSignUpRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.googleCreate(request))
    }

    override fun signInGoogle(request: GoogleSignInRequest): Observable<Token> {
        return requestWithErrorMapping(restApi.googleLogin(request))
    }

    override fun getUserData(id: Int, level: Int): Observable<UserResponse> {
        return requestWithErrorMapping(restApi.getUserData(id, level))
    }

    override fun updateUserLocation(id: Int, request: UpdateLocationRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.putUserLocation(id, request))
    }

    override fun updateUserDetails(id: Int, request: UpdateUserDetailsRequest): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.putUser(id, request))
    }
}