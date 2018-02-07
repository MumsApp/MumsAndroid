package com.mumsapp.domain.interactor.transformers

import io.reactivex.ObservableTransformer

interface UseCaseTransformerProvider {

    fun <T> get(): ObservableTransformer<T, T>
}