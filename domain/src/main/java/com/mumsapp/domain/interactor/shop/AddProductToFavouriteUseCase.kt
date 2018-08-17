package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class AddProductToFavouriteUseCase(val repository: AppRepository,
                                   @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                   schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<ShopFavouriteRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: ShopFavouriteRequest): Observable<EmptyResponse> {
        return repository.addProductToFavourite(param.productId)
    }
}