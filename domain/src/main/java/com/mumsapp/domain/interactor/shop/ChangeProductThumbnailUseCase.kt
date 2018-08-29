package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.AuthorizedUseCase
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.shop.ProductPhotoIdRequest
import com.mumsapp.domain.repository.AppRepository
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class ChangeProductThumbnailUseCase(val repository: AppRepository,
                                    @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                    schedulerProvider: SchedulerProvider)
    : AuthorizedUseCase<ProductPhotoIdRequest, EmptyResponse>(transformerProvider, schedulerProvider) {

    override fun createUseCaseObservable(param: ProductPhotoIdRequest): Observable<EmptyResponse> {
        return repository.setPhotoAsThumbnail(param.productId, param.photoId)
    }
}