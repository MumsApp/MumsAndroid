package com.mumsapp.domain.repository

import com.mumsapp.domain.model.lobby.LobbyResponse
import io.reactivex.Observable

interface AppRepository {

    fun getLobbyRooms(page: Int, perPage: Int): Observable<LobbyResponse>
}