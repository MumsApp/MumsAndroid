package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.shop.ProductCategoriesResponse
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetProductCategoriesUseCase(val repository: AppRepository,
                                  @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                  schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<EmptyRequest, ProductCategoriesResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: EmptyRequest): Observable<ProductCategoriesResponse> {
        return repository.getProductCategories()
    }
}