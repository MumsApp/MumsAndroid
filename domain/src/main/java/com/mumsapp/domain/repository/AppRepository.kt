package com.mumsapp.domain.repository

import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.lobby.*
import com.mumsapp.domain.model.shop.ProductCategoriesResponse
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.ProductsResponse
import com.mumsapp.domain.model.shop.SearchShopRequest
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

    fun createLobbyTopicPost(request: CreateLobbyTopicPostRequest): Observable<EmptyResponse>

    fun getProductCategories(): Observable<ProductCategoriesResponse>

    fun searchShopProducts(request: SearchShopRequest, page: Int, perPage: Int): Observable<ProductsResponse>

    fun addProductToFavourite(id: Int): Observable<EmptyResponse>

    fun removeProductFromFavourite(id: Int): Observable<EmptyResponse>

    fun getProductDetails(id: Int): Observable<ProductResponse>
}