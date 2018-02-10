package com.mumsapp.data.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
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
}