package com.mumsapp.domain.interactor.shop

import com.mumsapp.domain.interactor.BaseUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.Product
import com.mumsapp.domain.model.product.ProductResponse
import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Observable

class GetShopItemsUseCase(schedulerProvider: SchedulerProvider) : BaseUseCase<EmptyRequest, ProductResponse>(schedulerProvider) {

    override fun createUseCaseObservable(param: EmptyRequest): Observable<ProductResponse> {
        val response = ProductResponse(mockedList().toList())
        return Observable.defer { Observable.just(response) }
    }

    private fun mockedList() : Array<Product> = this[createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock(),
            createMock(), createMock(), createMock(), createMock(), createMock(), createMock(), createMock()] as Array<Product>

    private fun createMock() : Product {
        return Product(1, "Sample product", "Sample category", 55.toDouble(), "5 miles", "John N.")
    }

    operator fun get(vararg  array: Product) = array
}