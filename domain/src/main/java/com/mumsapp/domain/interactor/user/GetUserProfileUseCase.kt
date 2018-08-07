package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
import io.reactivex.Observable

class GetUserProfileUseCase(val userRepository: UserRepository, val sessionManager: SessionManager,
                            @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                            schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<Params, UserResponse>(transformerProvider, schedulerProvider) {


    override fun createUseCaseObservable(param: Params): Observable<UserResponse> {
        return userRepository.getUserData(param.id, param.level)
                .map {
                    val user = sessionManager.loadLoggedUser()
                    if(user?.data?.id == it.data.id) {
                        sessionManager.saveLoggedUser(it)
                    }
                    it
                }
    }

    data class Params(val id: Int, val level: Int): BaseRequest() {
        companion object {
            const val LEVEL_FULL = 7
        }
    }
}