package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.ProductItem
import com.mumsapp.domain.model.product.TemplateProductResponse
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetShopItemsUseCase(schedulerProvider: SchedulerProvider) : BaseUseCase<EmptyRequest, TemplateProductResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: EmptyRequest): Observable<TemplateProductResponse> {
        val response = TemplateProductResponse(mockedList().toList())
        return Observable.defer { Observable.just(response) }
    }

    private fun mockedList() : Array<ProductItem> = this[createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock()] as Array<ProductItem>

    private fun createMock() : ProductItem {
        return ProductItem(1, "Sample product", "Sample category", 55.toDouble(), "5 miles", "John N.")
    }

    operator fun get(vararg  array: ProductItem) = array
}