package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.*
import io.reactivex.Observable
import java.util.*

interface UserRepository {

    fun createUser(request: SignUpRequest): Observable<EmptyResponse>

    fun requestToken(request: SignInRequest): Observable<Token>

    fun signUpFacebook(request: FacebookSignUpRequest): Observable<EmptyResponse>

    fun signInFacebook(request: FacebookSignInRequest): Observable<Token>
}