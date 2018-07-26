package com.mumsapp.data.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.CreateLobbyRoomRequest
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.lobby.LobbyRoomResponse
import com.mumsapp.domain.model.lobby.SearchLobbyRequest
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SerializationHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override fun searchLobbyRooms(request: SearchLobbyRequest, page: Int, perPage: Int): Observable<LobbyResponse> {
        return requestWithErrorMapping(restApi.getLobbyRoomSearch(page, perPage, request.searchTerm,
                request.withDescription))
    }

    override fun addLobbyToFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postLobbyRoomIdFavourite(id))
    }

    override fun removeLobbyFromFavourite(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteLobbyRoomIdFavourite(id))
    }

    override fun joinLobbyRoom(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.postLobbyRoomIdJoin(id))
    }

    override fun leaveLobbyRoom(id: Int): Observable<EmptyResponse> {
        return requestWithErrorMapping(restApi.deleteLobbyRoomIdJoin(id))
    }

    override fun createLobbyRoom(request: CreateLobbyRoomRequest): Observable<LobbyRoomResponse> {
        val filePart = MultipartBody.Part.createFormData("file", request.file.name,
                RequestBody.create(MediaType.parse("image/*"), request.file))
        val apiRequest = restApi.postLobbyRoom(request.title, request.description, request.public,
                filePart)

        return requestWithErrorMapping(apiRequest)
    }
}