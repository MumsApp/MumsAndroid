package com.mumsapp.domain.interactor.lobby

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.lobby.LobbyItem
import com.mumsapp.domain.model.lobby.LobbyResponse
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetLobbyItemsUseCase(schedulerProvider: SchedulerProvider) : BaseUseCase<EmptyRequest, LobbyResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: EmptyRequest): Observable<LobbyResponse> {
        val response = LobbyResponse(mockedList().toList())
        return Observable.defer { Observable.just(response) }
    }

    private fun mockedList() : Array<LobbyItem> = this[createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock()] as Array<LobbyItem>

    private fun createMock() : LobbyItem {
        return LobbyItem(1, "Expectant Moms", "Lorem ipsum dolor sit amet, cons ectetur adipiscing elit. Nulla inter dum liberto tortor, quis.", false)
    }

    operator fun get(vararg  array: LobbyItem) = array
}