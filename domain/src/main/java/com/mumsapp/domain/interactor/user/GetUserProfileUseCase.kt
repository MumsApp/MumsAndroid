package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
import io.reactivex.Observable

class GetUserProfileUseCase(val userRepository: UserRepository, val sessionManager: SessionManager,
                            schedulerProvider: SchedulerProvider)
    : BaseUseCase<Params, UserResponse>(schedulerProvider) {


    override fun createUseCaseObservable(param: Params): Observable<UserResponse> {
        return userRepository.getUserData(param.id, param.level)
                .map {
                    sessionManager.saveLoggedUser(it)
                    it
                }
    }

    data class Params(val id: Int, val level: Int): BaseRequest() {
        companion object {
            val LEVEL_FULL = 7
        }
    }
}