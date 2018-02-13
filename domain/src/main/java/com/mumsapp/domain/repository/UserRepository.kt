package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.identity.Token
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.model.user.UserResponse
import io.reactivex.Observable
import java.util.*

interface UserRepository {

    fun createUser(request: SignUpRequest): Observable<EmptyResponse>

    fun requestToken(request: SignInRequest): Observable<Token>
}