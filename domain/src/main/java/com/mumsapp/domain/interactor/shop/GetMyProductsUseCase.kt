package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.shop.ProductsMyResponse
import com.mumsapp.domain.model.shop.ProductsResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetMyProductsUseCase(val repository: AppRepository,
                           @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                           schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<EmptyRequest, ProductsMyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: EmptyRequest): Observable<ProductsMyResponse> {
        return repository.getMyProducts()
    }
}