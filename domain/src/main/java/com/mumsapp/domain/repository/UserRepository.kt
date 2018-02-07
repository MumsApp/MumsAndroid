package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
import io.reactivex.Observable
import java.util.*

interface UserRepository {

    fun createUser(request: SignUpRequest): Observable<EmptyResponse>
}