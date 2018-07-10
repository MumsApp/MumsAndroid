package com.mumsapp.domain.interactor.user

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.user.UpdateLocationRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class UpdateUserLocationUseCase(val repository: UserRepository, val sessionManager: SessionManager,
                                @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                schedulerProvider: SchedulerProvider) :
        AuthorizedUseCase<UpdateLocationRequest, UserResponse>(transformerProvider, schedulerProvider) {


    override fun createUseCaseObservable(param: UpdateLocationRequest): Observable<UserResponse> {
        return repository.updateUserLocation(sessionManager.loadLoggedUser()!!.data.id, param)
                .map {
                    val user = sessionManager.loadLoggedUser()
                    val location = UserResponse.Location(param.name, param.placeId, param.latitude.toString(),
                            param.longitude.toString(), param.formattedAddress, param.enabled)
                    user!!.data.location = location
                    sessionManager.saveLoggedUser(user)
                    user
                }
    }
}