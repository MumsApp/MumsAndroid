package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.shop.ProductResponse
import com.mumsapp.domain.model.shop.UpdateShopProductRequest
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class UpdateShopProductUseCase(val repository: AppRepository,
                               @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                               schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<UpdateShopProductRequest, ProductResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: UpdateShopProductRequest): Observable<ProductResponse> {
        return repository.updateShopProduct(param)
    }
}