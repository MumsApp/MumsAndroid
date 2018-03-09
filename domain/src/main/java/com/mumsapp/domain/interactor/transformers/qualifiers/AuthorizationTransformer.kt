package com.mumsapp.domain.interactor.transformers.qualifiers

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import javax.inject.Qualifier

import java.lang.annotation.RetentionPolicy.RUNTIME

@Qualifier
@Documented
@Retention(RUNTIME)
annotation class AuthorizationTransformer