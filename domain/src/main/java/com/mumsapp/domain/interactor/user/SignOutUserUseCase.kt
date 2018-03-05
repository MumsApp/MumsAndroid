package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.utils.TokenPersistenceService
import io.reactivex.Observable

class SignOutUserUseCase(private val sessionManager: SessionManager,
                         private val tokenPersistenceService: TokenPersistenceService,
                         schedulerProvider: SchedulerProvider) :
        BaseUseCase<EmptyRequest, EmptyResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: EmptyRequest): Observable<EmptyResponse> {
        return Observable.fromCallable({
            sessionManager.clearUserSession()
            tokenPersistenceService.clearToken()
            EmptyResponse()
        })
    }
}