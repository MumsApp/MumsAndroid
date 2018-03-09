package com.mumsapp.domain.interactor

import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.BaseResponse
import com.mumsapp.domain.utils.SchedulerProvider

abstract class AuthorizedUseCase<Request : BaseRequest, Response : BaseResponse>(
        @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
        schedulerProvider: SchedulerProvider)
    : BaseUseCase<Request, Response>(schedulerProvider, transformerProvider) {


}