package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.*
import io.reactivex.Observable

interface AppRepository {

    fun getLobbyRooms(page: Int, perPage: Int): Observable<LobbyResponse>

    fun searchLobbyRooms(request: SearchLobbyRequest, page: Int, perPage: Int): Observable<LobbyResponse>

    fun addLobbyToFavourite(id: Int): Observable<EmptyResponse>

    fun removeLobbyFromFavourite(id: Int): Observable<EmptyResponse>

    fun joinLobbyRoom(id: Int): Observable<EmptyResponse>

    fun leaveLobbyRoom(id: Int): Observable<EmptyResponse>

    fun createLobbyRoom(request: CreateLobbyRoomRequest): Observable<LobbyRoomResponse>

    fun deleteLobbyRoom(id: Int): Observable<EmptyResponse>

    fun getLobbyRoomTopics(lobbyRoomId: Int, page: Int, perPage: Int): Observable<LobbyRoomTopicsResponse>

    fun createLobbyRoomTopic(request: CreateLobbyRoomTopicRequest): Observable<EmptyResponse>

    fun getLobbyRoomTopicPosts(lobbyRoomId: Int, lobbyRoomTopicId: Int, page: Int, perPage: Int): Observable<LobbyRoomTopicPostsResponse>
}