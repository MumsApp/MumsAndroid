package com.mumsapp.data.repository

import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import javax.inject.Inject

class AppRepositoryImpl : BaseRestRepository, AppRepository {

    private val restApi: PublicRestApi

    @Inject
    constructor(restApi: PublicRestApi, exceptionDispatcher: ExceptionDispatcher,
                serializationHelper: SerializationHelper,
                resourceRepository: ResourceRepository) : super(exceptionDispatcher, serializationHelper, resourceRepository) {
        this.restApi = restApi
    }

    override fun getLobbyRooms(page: Int, perPage: Int): Observable<LobbyResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomPage(page, perPage))
    }
}