package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.SearchShopRequest
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class SearchShopProductsUseCase(val repository: AppRepository,
                                @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<SearchShopRequest, ProductResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: SearchShopRequest): Observable<ProductResponse> {
        return repository.searchShopProducts(param, param.page, param.perPage)
    }
}