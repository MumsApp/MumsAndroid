package com.mumsapp.data.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl : UserRepository {

    private val restApi: PublicRestApi

    @Inject
    constructor(restApi: PublicRestApi) {
        this.restApi = restApi
    }

    override fun createUser(request: SignUpRequest): Observable<EmptyResponse> {
        return restApi.createUser(request)
    }
}