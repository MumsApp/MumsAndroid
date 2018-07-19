package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.model.lobby.SearchLobbyRequest
import io.reactivex.Observable

interface AppRepository {

    fun getLobbyRooms(page: Int, perPage: Int): Observable<LobbyResponse>

    fun searchLobbyRooms(request: SearchLobbyRequest, page: Int, perPage: Int): Observable<LobbyResponse>

    fun addLobbyToFavourite(id: Int): Observable<EmptyResponse>

    fun removeLobbyFromFavourite(id: Int): Observable<EmptyResponse>

    fun joinLobbyRoom(id: Int): Observable<EmptyResponse>

    fun leaveLobbyRoom(id: Int): Observable<EmptyResponse>
}